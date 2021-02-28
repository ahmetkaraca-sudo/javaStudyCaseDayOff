package com.karaca.daysofcalculator.entity;

import com.karaca.daysofcalculator.enums.DayOffStatus;
import com.sun.istack.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class DayOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private DayOffStatus status;

    private double count;

    private String requestedWorkingDay;

    @ManyToOne(cascade = CascadeType.ALL)
    @Nullable
    private User approvedBy;

    public DayOff() {
    }

    public DayOff(User user,
                  LocalDate startDate,
                  LocalDate endDate,
                  DayOffStatus status,
                  double count,
                  String requestedWorkingDay,
                  User approvedBy) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.count = count;
        this.requestedWorkingDay = requestedWorkingDay;
        this.approvedBy = approvedBy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public DayOffStatus getStatus() {
        return status;
    }

    public void setStatus(DayOffStatus status) {
        this.status = status;
    }

    public String getRequestedWorkingDay() {
        return requestedWorkingDay;
    }

    public void setRequestedWorkingDay(String requestedWorkingDay) {
        this.requestedWorkingDay = requestedWorkingDay;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
}
