package com.sambat.demo.Service.security;

import com.sambat.demo.Dto.User.UserDto;
import com.sambat.demo.Dto.auth.AuthDto;
import com.sambat.demo.Dto.auth.AuthResponseDto;
import com.sambat.demo.Entity.UserEntity;
import com.sambat.demo.Exception.Model.DuplicatedException;
import com.sambat.demo.Mapper.UserMapper;
import com.sambat.demo.Repository.UserRepository;
import com.sambat.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserService userService;

    public AuthResponseDto register(UserDto payload){
        if(userRepository.existsByName(payload.getName())){
            throw new DuplicatedException("username already existed");
        }
        if(userRepository.existsByEmail(payload.getEmail())){
            throw new DuplicatedException("email already existed");
        }

        UserEntity user = userMapper.userDtoToEntity(payload);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity createduser = userRepository.save(user);
        String token = jwtUtil.generateToken(createduser);

        return new AuthResponseDto(token, null);
    }

    public AuthResponseDto login(AuthDto payload){
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(payload.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponseDto(token, null);
    }
}
