package com.karaca.daysofcalculator.service;

import com.karaca.daysofcalculator.Dto.UserCreateDto;
import com.karaca.daysofcalculator.entity.GrantedAuthorities;
import com.karaca.daysofcalculator.repository.GrantedAuthoritiesRepository;
import com.karaca.daysofcalculator.repository.UserRepository;
import com.karaca.daysofcalculator.security.ApplicationUserRole;
import com.karaca.daysofcalculator.service.Impl.UserServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder bCryptPasswordEncoder;
    @Mock
    private GrantedAuthoritiesRepository grantedAuthoritiesRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_saveUser() {
        UserCreateDto userCreate = new UserCreateDto("test", "test", "123", "test_username", "test_password");
        GrantedAuthorities role_user = new GrantedAuthorities(1L, ApplicationUserRole.USER.getRole());

        Mockito.when(userRepository.existsByUsername(userCreate.getUsername())).thenReturn(false);
        Mockito.when(grantedAuthoritiesRepository.findByRole(ApplicationUserRole.USER.getRole())).thenReturn(role_user);
        Mockito.when(bCryptPasswordEncoder.encode(userCreate.getPassword())).thenReturn("ENCODED_PASSWORD");

        userService.saveUser(userCreate);

        Mockito.verify(userRepository).existsByUsername(userCreate.getUsername());
        Mockito.verify(grantedAuthoritiesRepository).findByRole(ApplicationUserRole.USER.getRole());
        Mockito.verify(bCryptPasswordEncoder).encode(userCreate.getPassword());
    }
}