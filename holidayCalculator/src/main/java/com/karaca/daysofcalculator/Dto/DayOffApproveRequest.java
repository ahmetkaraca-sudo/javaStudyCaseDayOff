package com.karaca.daysofcalculator.Dto;

public class DayOffApproveRequest {

    private String approve;
    private Long dayOfId;

    public DayOffApproveRequest(String approve, Long dayOfId) {
        this.approve = approve;
        this.dayOfId = dayOfId;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public Long getDayOfId() {
        return dayOfId;
    }

    public void setDayOfId(Long dayOfId) {
        this.dayOfId = dayOfId;
    }
}
