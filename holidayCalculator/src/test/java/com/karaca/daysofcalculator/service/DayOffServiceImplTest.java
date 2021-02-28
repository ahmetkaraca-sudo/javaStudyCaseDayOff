package com.karaca.daysofcalculator.service;

import com.karaca.daysofcalculator.Dto.DayOffApproveRequest;
import com.karaca.daysofcalculator.Dto.DayOffRequestDto;
import com.karaca.daysofcalculator.Dto.DayOffResponseDto;
import com.karaca.daysofcalculator.entity.DayOff;
import com.karaca.daysofcalculator.entity.PublicHoliday;
import com.karaca.daysofcalculator.entity.User;
import com.karaca.daysofcalculator.enums.DayOffStatus;
import com.karaca.daysofcalculator.exception.InvalidDayOffBoundException;
import com.karaca.daysofcalculator.repository.DayOffRepository;
import com.karaca.daysofcalculator.repository.PublicHolidayRepository;
import com.karaca.daysofcalculator.repository.UserRepository;
import com.karaca.daysofcalculator.service.Impl.DayOffServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class DayOffServiceImplTest {

    @Mock
    DayOffRepository dayOffRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PublicHolidayRepository publicHolidayRepository;

    @InjectMocks
    DayOffServiceImpl dayOffService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_approveOffDay() {
        // prepare
        DayOffApproveRequest dayOffAproveRequest = new DayOffApproveRequest("ACCEPTED", 1L);
        String requestedDayOff = "1,1.0|2,1.0|3,0.5";
        Optional<DayOff> returnDayOff = Optional.of(new DayOff(null, LocalDate.now(), LocalDate.now(), DayOffStatus.PENDING, 3, requestedDayOff, null));
        String username = "test_manager_user";
        User user = new User(1L, "manager", "manager", "123", LocalDate.now());
        // when
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(dayOffRepository.findById(dayOffAproveRequest.getDayOfId())).thenReturn(returnDayOff);

        // run the method
        dayOffService.approveOffDay(dayOffAproveRequest, username);

        // then
        Assertions.assertEquals(returnDayOff.get().getStatus(), DayOffStatus.ACCEPTED);
    }

    @Test
    void test_offDayEntry() {
        LocalDate startDate = LocalDate.parse("2021-02-01");
        LocalDate endDate = LocalDate.parse("2021-02-07");
        DayOffRequestDto dayOffRequestDto = new DayOffRequestDto(startDate, endDate);
        Optional<User> user = Optional.of(new User(1L, "test_name", "test_surname", "123", LocalDate.now().minusDays(60)));
        String requestedDayOff = "1,1.0|2,1.0";
        DayOff dayOff = new DayOff(user.get(), LocalDate.parse("2021-01-05"), LocalDate.parse("2021-01-06"), DayOffStatus.PENDING, 2, requestedDayOff, null);
        PublicHoliday pHoliday1 = new PublicHoliday(LocalDate.parse("2021-02-03"), 0.5);
        PublicHoliday pHoliday2 = new PublicHoliday(LocalDate.parse("2021-02-04"), 1.0);
        PublicHoliday pHoliday3 = new PublicHoliday(LocalDate.parse("2021-02-05"), 1.0);
        String username = "test_user";

        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(publicHolidayRepository.findAll()).thenReturn(Arrays.asList(pHoliday1, pHoliday2, pHoliday3));
        Mockito.when(dayOffRepository.findAllByUserIdAndStartDateAfterAndEndDateBefore(Mockito.anyLong(), Mockito.any(), Mockito.any()))
                .thenReturn(Arrays.asList(dayOff));
        ReflectionTestUtils.setField(dayOffService, "zeroOne", 5);

        dayOffService.offDayEntry(dayOffRequestDto, username);
    }

    @Test
    void test_getAllOffDays() {
        String username = "username";
        String s = "1,1.0-2,0.5-3,1.0-4,0.5";
        User user = new User("test", "test", "test" , "test");
        DayOff dayOff1 = new DayOff(null,LocalDate.now(),null,DayOffStatus.ACCEPTED,0.0,s,null);
        DayOff dayOff2 = new DayOff(null,LocalDate.now(),null,DayOffStatus.ACCEPTED,0.0,"1,1.0-2,0.5",null);
        DayOff dayOff3 = new DayOff(null,LocalDate.now(),null,DayOffStatus.PENDING,0.0,"1,1.0",null);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(dayOffRepository.findAllByUser(user)).thenReturn(Arrays.asList(dayOff1, dayOff2, dayOff3));

        List<DayOffResponseDto> actual = dayOffService.getAllOffDays(username);

        long acceptedCount = actual.stream().filter(x -> x.getStatus().equals(DayOffStatus.ACCEPTED.getValue().toLowerCase())).count();
        long pendingCount = actual.stream().filter(x -> x.getStatus().equals(DayOffStatus.PENDING.getValue().toLowerCase())).count();
        Assertions.assertEquals(acceptedCount, 2L);
        Assertions.assertEquals(pendingCount, 1L);
        Assertions.assertEquals(s.split("-").length, actual.get(0).getDurations().size());
    }

    @Test
    void test_offDayEntry_when_startDate_after_endDate() {
        LocalDate startDate = LocalDate.parse("2021-02-01");
        LocalDate endDate = LocalDate.parse("2021-02-07");
        DayOffRequestDto dayOffRequest = new DayOffRequestDto(endDate, startDate);
        String username = "test";

        Assertions.assertThrows(InvalidDayOffBoundException.class, () -> dayOffService.offDayEntry(dayOffRequest, username));
    }
}