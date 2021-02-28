package com.karaca.daysofcalculator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    @JsonIgnore
    private String identityId;
    private LocalDate startDayOfwork;

    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;

    @PrePersist
    private void onCreate() {
        this.startDayOfwork = LocalDate.now();
    }

    @OneToOne
    private GrantedAuthroties grantedAuthroties;

    public User() {
    }

    public User(Long id, String name, String surname, String identityId, LocalDate startDayOfwork) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.identityId = identityId;
        this.startDayOfwork = startDayOfwork;
    }

    public User(String name,
                String surname,
                String identityId,
                String username) {
        this.name = name;
        this.surname = surname;
        this.identityId = identityId;
        this.startDayOfwork = LocalDate.now();
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(grantedAuthroties.getRole()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GrantedAuthroties getGrantedAuthroties() {
        return grantedAuthroties;
    }

    public void setGrantedAuthroties(GrantedAuthroties grantedAuthroties) {
        this.grantedAuthroties = grantedAuthroties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public LocalDate getStartDayOfwork() {
        return startDayOfwork;
    }

    public void setStartDayOfwork(LocalDate startDayOfwork) {
        this.startDayOfwork = startDayOfwork;
    }
}
