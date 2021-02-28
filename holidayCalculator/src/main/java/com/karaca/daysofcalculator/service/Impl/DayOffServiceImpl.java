package com.karaca.daysofcalculator.service.Impl;

import com.karaca.daysofcalculator.Dto.DayOffApproveRequest;
import com.karaca.daysofcalculator.Dto.DayOffDurationDto;
import com.karaca.daysofcalculator.Dto.DayOffRequestDto;
import com.karaca.daysofcalculator.Dto.DayOffResponseDto;
import com.karaca.daysofcalculator.entity.DayOff;
import com.karaca.daysofcalculator.entity.PublicHoliday;
import com.karaca.daysofcalculator.entity.User;
import com.karaca.daysofcalculator.enums.DayOffStatus;
import com.karaca.daysofcalculator.exception.*;
import com.karaca.daysofcalculator.repository.DayOffRepository;
import com.karaca.daysofcalculator.repository.PublicHolidayRepository;
import com.karaca.daysofcalculator.repository.UserRepository;
import com.karaca.daysofcalculator.service.DayOffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DayOffServiceImpl implements DayOffService {

    @Value("${employe.service.time.leave.period.zero.one}")
    private int zeroOne;

    @Value("${employe.service.time.leave.period.one.five}")
    private int oneFive;

    @Value("${employe.service.time.leave.period.six.ten}")
    private int sixTen;

    @Value("${employe.service.time.leave.period.ten.more}")
    private int tenMore;

    private static final double OFF_DAY_DURATION = 0.0;
    private static final double WORKING_DAY_DURATION = 1.0;
    private static final String DELIMITER_DAY = "-";
    private static final String DELIMITER_DURATION = ",";

    private final DayOffRepository dayOffRepository;
    private final UserRepository userRepository;
    private final PublicHolidayRepository publicHolidayRepository;

    public DayOffServiceImpl(DayOffRepository dayOffRepository, UserRepository userRepository, PublicHolidayRepository publicHolidayRepository) {
        this.dayOffRepository = dayOffRepository;
        this.userRepository = userRepository;
        this.publicHolidayRepository = publicHolidayRepository;
    }

    public void offDayEntry(DayOffRequestDto dayOffRequest, String username) {
        if (dayOffRequest.getStartDate().compareTo(dayOffRequest.getEndDate()) > 0) {
            throw new InvalidDayOffBoundException();
        }

        if (dayOffRequest.getStartDate().compareTo(LocalDate.now()) > 0) {
            throw new InvalidDayOffException();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        double remainingDayOff = remainingDayOff(user.getId(), user.getStartDayOfwork());
        double requestedDayOff = calculateWorkingDay(dayOffRequest.getStartDate(), dayOffRequest.getEndDate());
        if(requestedDayOff == 0) {
            throw new InvalidRequestedDayOff();
        }else if (remainingDayOff - requestedDayOff >= 0) {
            DayOff dayOff = new DayOff(user,
                    dayOffRequest.getStartDate(),
                    dayOffRequest.getEndDate(),
                    DayOffStatus.PENDING,
                    requestedDayOff,
                    getWorkingDayListWithDurationAsString(dayOffRequest.getStartDate(), dayOffRequest.getEndDate()),
                    null);
            dayOffRepository.save(dayOff);
        } else {
            throw new NotEnoughDayOffException();
        }
    }

    private String getWorkingDayListWithDurationAsString(LocalDate startDate, LocalDate endDate) {
        return getWorkingDayListWithDuration(startDate, endDate)
                .stream().map(dayOffDuration -> numberOfDaysBetweenDates(startDate, dayOffDuration.getDate()) + DELIMITER_DURATION + dayOffDuration.getDuration())
                .collect(Collectors.joining(DELIMITER_DAY));
    }

    private List<DayOffDurationDto> getWorkingDayListWithDuration(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, Double> publicHolidays = publicHolidayToMap(publicHolidayRepository.findAll());
        long numberOfDays = numberOfDaysBetweenDates(startDate, endDate);
        return IntStream.range(0, (int) numberOfDays)
                .mapToObj(startDate::plusDays)
                .filter(date ->
                        publicHolidays.getOrDefault(date, OFF_DAY_DURATION) < WORKING_DAY_DURATION
                                && !date.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                                && !date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                )
                .map(date -> new DayOffDurationDto(date, publicHolidays.getOrDefault(date, WORKING_DAY_DURATION)))
                .collect(Collectors.toList());
    }

    private double calculateWorkingDay(LocalDate startDate, LocalDate endDate) {
        long numberOfDay = numberOfDaysBetweenDates(startDate, endDate);
        Map<LocalDate, Double> publicHolidays = publicHolidayToMap(publicHolidayRepository.findAll());
        return IntStream.range(0, (int) numberOfDay)
                .mapToObj(startDate::plusDays)
                .map(date -> getDurationOfWorkingDay(date, publicHolidays))
                .reduce((double) 0, Double::sum);
    }

    private long numberOfDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
    }

    private double getDurationOfWorkingDay(LocalDate date, Map<LocalDate, Double> publicHolidays) {
        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            return OFF_DAY_DURATION;
        } else {
            return WORKING_DAY_DURATION - publicHolidays.getOrDefault(date, OFF_DAY_DURATION);
        }
    }

    private Map<LocalDate, Double> publicHolidayToMap(List<PublicHoliday> publicHolidays) {
        return publicHolidays.stream()
                .collect(HashMap::new, (map, holiday) -> map.put(holiday.getDate(), holiday.getDuration()), Map::putAll);
    }

    private double remainingDayOff(Long userId, LocalDate startDayOfWork) {
        long servedYear = ChronoUnit.YEARS.between(startDayOfWork, LocalDate.now());
        double usedDayOff = dayOffRepository.findAllByUserIdAndStartDateAfterAndEndDateBefore(userId,
                startDayOfWork.plusYears(servedYear == 1L ? 0 : servedYear),
                startDayOfWork.plusYears(servedYear + 1L))
                .stream()
                .filter(offDay -> offDay.getStatus().equals(DayOffStatus.ACCEPTED) || offDay.getStatus().equals(DayOffStatus.PENDING))
                .map(DayOff::getCount)
                .reduce((double) 0, Double::sum);

        int deservedDayOff = deservedDayOffByYear((short) servedYear);
        return deservedDayOff - usedDayOff;
    }

    private int deservedDayOffByYear(short year) {
        if (year < 1) {
            return zeroOne;
        } else if (year <= 5) {
            return oneFive;
        } else if (year <= 10) {
            return sixTen;
        } else {
            return tenMore;
        }
    }

    @Override
    public void approveOffDay(DayOffApproveRequest dayOffAproveRequest, String username) {
        userRepository.findByUsername(username).ifPresent(user ->
                dayOffRepository.findById(dayOffAproveRequest.getDayOfId()).ifPresent(dOff -> {
                    if (!dOff.getStatus().equals(DayOffStatus.PENDING)) {
                        throw new DayOffApprovePendingException();
                    }
                    dOff.setStatus(DayOffStatus.fromValue(dayOffAproveRequest.getApprove()));
                    dOff.setApprovedBy(user);
                    dayOffRepository.save(dOff);
                })
        );

    }

    @Override
    public List<DayOffResponseDto> getAllOffDays(String username) {
        List<DayOffResponseDto> dayOffResponseDtos = new ArrayList<>();
        userRepository.findByUsername(username).ifPresent(user ->
                dayOffResponseDtos.addAll(
                        dayOffRepository.findAllByUser(user).stream()
                                .map(this::toDayOffResponseDTO)
                                .collect(Collectors.toList())
                )
        );
        return dayOffResponseDtos;
    }

    private DayOffResponseDto toDayOffResponseDTO(DayOff dayOff) {
        String[] daysWithDuration = dayOff.getRequestedWorkingDay().split(DELIMITER_DAY);
        List<DayOffDurationDto> collect = Arrays.stream(daysWithDuration)
                .map(item -> {
                    String[] dayDuration = item.split(DELIMITER_DURATION);
                    return new DayOffDurationDto(
                            dayOff.getStartDate().plusDays(Integer.parseInt(dayDuration[0])),
                            Double.parseDouble(dayDuration[1])
                    );
                })
                .collect(Collectors.toList());
        return new DayOffResponseDto(dayOff.getStatus().getValue(), collect);
    }
}
