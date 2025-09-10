package com.sambat.demo.Service.security;

import com.sambat.demo.Dto.User.UserDto;
import com.sambat.demo.Entity.UserEntity;
import com.sambat.demo.Exception.Model.DuplicatedException;
import com.sambat.demo.Mapper.UserMapper;
import com.sambat.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String register(UserDto payload){
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

        return token;
    }
}
