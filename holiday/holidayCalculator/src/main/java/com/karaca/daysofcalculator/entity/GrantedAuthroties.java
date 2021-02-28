package com.karaca.daysofcalculator.entity;

import javax.persistence.*;

@Entity
public class GrantedAuthroties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Enumerated(EnumType.STRING)
    private String role;

    public GrantedAuthroties() {
    }

    public GrantedAuthroties(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
