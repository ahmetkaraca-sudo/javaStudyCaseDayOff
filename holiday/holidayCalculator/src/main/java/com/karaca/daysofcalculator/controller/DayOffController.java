package com.karaca.daysofcalculator.controller;

import com.karaca.daysofcalculator.Dto.DayOffApproveRequest;
import com.karaca.daysofcalculator.Dto.DayOffRequestDto;
import com.karaca.daysofcalculator.Dto.DayOffResponseDto;
import com.karaca.daysofcalculator.service.DayOffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/dayOff")
public class DayOffController {

    private final DayOffService dayOffService;

    public DayOffController(DayOffService dayOffService) {
        this.dayOffService = dayOffService;
    }

    @PostMapping("/request")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public ResponseEntity<Void> requestDayOff(@RequestBody DayOffRequestDto dayOffRequest, Principal principal) {
        dayOffService.offDayEntry(dayOffRequest, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Boolean> approveDayOff(@RequestBody DayOffApproveRequest dayOffAproveRequest, Principal principal) {
        dayOffService.approveOffDay(dayOffAproveRequest, principal.getName());
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public List<DayOffResponseDto> getAllDayOff(Principal principal) {
        return dayOffService.getAllOffDays(principal.getName());
    }
}
