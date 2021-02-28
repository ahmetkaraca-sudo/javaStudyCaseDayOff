package com.karaca.daysofcalculator.repository;

import com.karaca.daysofcalculator.entity.GrantedAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantedAuthoritiesRepository extends JpaRepository<GrantedAuthorities, Long> {

    GrantedAuthorities findByRole(String role);
}
