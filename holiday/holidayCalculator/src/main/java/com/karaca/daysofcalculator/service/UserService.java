package com.karaca.daysofcalculator.service;

import com.karaca.daysofcalculator.Dto.UserCreateDto;
import com.karaca.daysofcalculator.Dto.UserLoginDto;
import com.karaca.daysofcalculator.entity.User;

public interface UserService {
    User saveUser(UserCreateDto user);

    String loginUser(UserLoginDto loginDto);
}
