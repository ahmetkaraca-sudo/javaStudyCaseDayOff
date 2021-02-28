package com.karaca.daysofcalculator.controller;

import com.karaca.daysofcalculator.Dto.DayOffRequestDto;
import com.karaca.daysofcalculator.exception.InvalidDayOffBoundException;
import com.karaca.daysofcalculator.service.DayOffService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
class DayOffControllerTest {

    @Mock
    DayOffService dayOffService;

    @InjectMocks
    DayOffController dayOffController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(dayOffController).build();
    }

    @Test
    void requestDayOff() throws Exception {
        String requestBodyJson = "{ \"startDate\":\"2020-01-05\", \"endDate\":\"2020-01-07\" }";

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("test");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/dayOff/request")
                .content(requestBodyJson)
                .principal(mockPrincipal)
                .header("Content-Type", "application/json");

        Mockito.doNothing().when(dayOffService).offDayEntry(Mockito.any(), Mockito.anyString());

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();
    }

//    @Test
//    void exrequestDayOff() throws Exception {
//        String requestBodyJson = "{ \"startDate\":\"2020-01-05\", \"endDate\":\"2020-01-07\" }";
//        DayOffRequestDto dayOffRequestDto = new DayOffRequestDto(LocalDate.parse("2020-01-05"), LocalDate.parse("2020-01-07"));
//        Principal mockPrincipal = Mockito.mock(Principal.class);
//        Mockito.when(mockPrincipal.getName()).thenReturn("test");
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/dayOff/request")
//                .content(requestBodyJson)
//                .principal(mockPrincipal)
//                .header("Content-Type", "application/json");
//
//
//        Mockito.doThrow(new InvalidDayOffBoundException()).when(dayOffService).offDayEntry(dayOffRequestDto, "test");
//
//        Assertions.assertThrows(InvalidDayOffBoundException.class, () -> mockMvc
//                .perform(requestBuilder)
//                .andExpect(status().isOk()).andReturn());
//    }

}