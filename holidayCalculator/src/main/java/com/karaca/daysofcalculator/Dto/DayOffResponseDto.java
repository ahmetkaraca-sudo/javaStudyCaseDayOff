package com.karaca.daysofcalculator.Dto;

import java.util.List;

public class DayOffResponseDto {
    private String status;
    private List<DayOffDurationDto> durations;

    public DayOffResponseDto(String status, List<DayOffDurationDto> durations) {
        this.status = status;
        this.durations = durations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DayOffDurationDto> getDurations() {
        return durations;
    }

    public void setDurations(List<DayOffDurationDto> durations) {
        this.durations = durations;
    }
}
