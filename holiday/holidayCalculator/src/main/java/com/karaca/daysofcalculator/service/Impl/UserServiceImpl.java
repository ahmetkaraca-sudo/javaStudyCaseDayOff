package com.karaca.daysofcalculator.service.Impl;

import antlr.StringUtils;
import com.karaca.daysofcalculator.Dto.UserCreateDto;
import com.karaca.daysofcalculator.Dto.UserLoginDto;
import com.karaca.daysofcalculator.entity.GrantedAuthroties;
import com.karaca.daysofcalculator.entity.User;
import com.karaca.daysofcalculator.exception.UserAlreadyExistsException;
import com.karaca.daysofcalculator.repository.GrantedAuthrotiesRepository;
import com.karaca.daysofcalculator.repository.UserRepository;
import com.karaca.daysofcalculator.security.ApplicationUserRole;
import com.karaca.daysofcalculator.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserServiceImpl implements UserService {

    @Value("${application.jwt.tokenPrefix}")
    private String tokenPrefix;

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final GrantedAuthrotiesRepository grantedAuthrotiesRepository;
    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder bCryptPasswordEncoder,
                           GrantedAuthrotiesRepository grantedAuthrotiesRepository,
                           AuthenticationManager authenticationManager,
                           SecretKey secretKey
    ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.grantedAuthrotiesRepository = grantedAuthrotiesRepository;
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
    }

    public User saveUser(UserCreateDto user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException();
        }

        User newUser = new User(user.getName(), user.getSurname(), user.getIdentityId(), user.getUsername());

        GrantedAuthroties byRole = grantedAuthrotiesRepository.findByRole(ApplicationUserRole.USER.getRole());
        newUser.setGrantedAuthroties(byRole);
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public String loginUser(UserLoginDto loginDto) {
        String token = "";
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate.isAuthenticated()) {
            token = Jwts.builder()
                    .setSubject(authenticate.getName())
                    .claim("authorities", authenticate.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                    .signWith(secretKey)
                    .compact();
        }

        return token.equals("") ? token : tokenPrefix + token;
    }
}
