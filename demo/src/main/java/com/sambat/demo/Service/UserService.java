package com.sambat.demo.Service;

import com.sambat.demo.Dto.User.ChangePasswordDto;
import com.sambat.demo.Dto.User.UpdateUserDto;
import com.sambat.demo.Dto.User.UserResponseDto;
import com.sambat.demo.Entity.UserEntity;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Mapper.UserMapper;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Repository.UserRepository;
import com.sambat.demo.Service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<BaseDataResponseModel> getUsers(){
        List<UserEntity> users = userRepository.findAll();
        if(users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseDataResponseModel("fail", "No Users Found", null));
        }

        List<UserResponseDto> userDtos =  userMapper.userEntityToDtoList(users);

        return ResponseEntity.ok( new BaseDataResponseModel("success", "All Users", userDtos));
    }

    public ResponseEntity<BaseDataResponseModel> getUserById(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundHandler("user not found " + id));

//        String token = jwtUtil.generateToken(user);
//        System.out.println("token: " + token);

        UserResponseDto userResponseDto = userMapper.userEntityToDto(user);
        return ResponseEntity.ok(new BaseDataResponseModel("success", "user found", userResponseDto));
    }



    public ResponseEntity<BaseResponseModel> deleteUserById(Long id){
        if(!userRepository.existsById(id)){
            throw new NotFoundHandler("user not found" + id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(new BaseResponseModel("success", "user deleted"));
    }

    public ResponseEntity<BaseResponseModel> updateUserById(Long id, UpdateUserDto payload){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundHandler("user not found" + id));
        userMapper.updateUserEntityFromDto(user, payload);
        userRepository.save(user);
        return ResponseEntity.ok(new BaseResponseModel("success", "user updated"));
    }

    public ResponseEntity<BaseDataResponseModel> searchUser(String name) {
        String nameCheck = name != null ? name.toLowerCase() : null;
        List<UserEntity> user = userRepository.findByUserName(nameCheck);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseDataResponseModel("success", "user found", user));
    }

    public ResponseEntity<BaseResponseModel> changePassword(Long id, ChangePasswordDto payload){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundHandler("user not found"));

        if(!Objects.equals(user.getPassword(), payload.getOldPassword())){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new BaseResponseModel("fail", "old pass not correct"));
        }

        if(!Objects.equals(payload.getNewPassword(), payload.getConfirmPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponseModel("fail", "new and comfirm pass not the same"));
        }

        userMapper.changePassword(user, payload.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.ok(new BaseResponseModel("success", "password change successfully"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("user not found: " + username);
                });
    }
}
