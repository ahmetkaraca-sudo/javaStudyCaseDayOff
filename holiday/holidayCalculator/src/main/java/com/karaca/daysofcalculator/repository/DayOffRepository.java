package com.karaca.daysofcalculator.repository;

import com.karaca.daysofcalculator.entity.DayOff;
import com.karaca.daysofcalculator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, Long> {

    List<DayOff> findAllByUserIdAndStartDateAfterAndEndDateBefore(Long userId, LocalDate startDate, LocalDate endDate);

    List<DayOff> findAllByUser(User user);
}
