package com.karaca.daysofcalculator.service;

import com.karaca.daysofcalculator.Dto.DayOffApproveRequest;
import com.karaca.daysofcalculator.Dto.DayOffRequestDto;
import com.karaca.daysofcalculator.Dto.DayOffResponseDto;

import java.util.List;

public interface DayOffService {

    void offDayEntry(DayOffRequestDto dayOffRequest, String username);

    void approveOffDay(DayOffApproveRequest dayOffAproveRequest, String username);

    List<DayOffResponseDto> getAllOffDays(String username);
}
