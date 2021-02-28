package com.karaca.daysofcalculator.service;

import com.karaca.daysofcalculator.entity.User;
import com.karaca.daysofcalculator.repository.UserRepository;
import com.karaca.daysofcalculator.service.Impl.CustomUserDetailsService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_loadUserByUsername() {
        String username = "test_username";
        User user = new User();

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        Assertions.assertNotNull(userDetails);
    }

}