package com.karaca.daysofcalculator.repository;

import com.karaca.daysofcalculator.entity.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {

}
