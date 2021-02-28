package com.karaca.daysofcalculator.repository;

import com.karaca.daysofcalculator.entity.GrantedAuthroties;
import com.karaca.daysofcalculator.security.ApplicationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantedAuthrotiesRepository extends JpaRepository<GrantedAuthroties, Long> {

    GrantedAuthroties findByRole(String role);
}
