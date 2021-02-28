package com.karaca.daysofcalculator.repository;

import com.karaca.daysofcalculator.entity.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {
    List<PublicHoliday> findAllByDateAfterAndDateBefore(LocalDate startDate, LocalDate endDate);

}
