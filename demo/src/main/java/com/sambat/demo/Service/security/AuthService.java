package com.sambat.demo.Service.security;

import com.sambat.demo.Dto.User.UserDto;
import com.sambat.demo.Dto.auth.AuthDto;
import com.sambat.demo.Dto.auth.AuthResponseDto;
import com.sambat.demo.Dto.refresh.RefreshTokenDto;
import com.sambat.demo.Entity.RefreshTokenEntity;
import com.sambat.demo.Entity.UserEntity;
import com.sambat.demo.Exception.Model.DuplicatedException;
import com.sambat.demo.Exception.Model.UnprocessEntityException;
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
    @Autowired
    private RefreshTokenService refreshTokenService;

    public AuthResponseDto register(UserDto payload){
        if(userRepository.existsByName(payload.getName())){
            throw new DuplicatedException("username already existed");
        }
        if(userRepository.existsByEmail(payload.getEmail())){
            throw new DuplicatedException("email already existed");
        }

        UserEntity user = userMapper.userDtoToEntity(payload);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity createdUser = userRepository.save(user);
        String accessToken = jwtUtil.generateToken(createdUser);

        RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(createdUser);
        String refreshToken = refreshTokenEntity.getToken();

        return new AuthResponseDto(accessToken, refreshToken);
    }

    public AuthResponseDto login(AuthDto payload){
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(payload.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        UserEntity userEntity = (UserEntity) userDetails;
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userEntity);

        return new AuthResponseDto(token, refreshToken.getToken());
    }

    public AuthResponseDto refreshToken(RefreshTokenDto payload){
        RefreshTokenEntity token = refreshTokenService.getToken(payload.getRefreshToken());

        if(!token.isValidate()){
            throw new UnprocessEntityException("token already revoke or expired");
        }

        UserEntity user = token.getUser();
        String accessToken = jwtUtil.generateToken(user);

        RefreshTokenEntity refreshToken = refreshTokenService.rotateToken(token, user);

        return new AuthResponseDto(accessToken, refreshToken.getToken());
    }
}
