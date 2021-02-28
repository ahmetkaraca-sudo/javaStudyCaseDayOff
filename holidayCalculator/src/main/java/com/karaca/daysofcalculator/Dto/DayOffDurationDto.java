package com.karaca.daysofcalculator.Dto;

import java.time.LocalDate;

public class DayOffDurationDto {
    private LocalDate date;
    private double duration;

    public DayOffDurationDto(LocalDate date, double duration) {
        this.date = date;
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
