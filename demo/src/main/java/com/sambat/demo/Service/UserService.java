package com.sambat.demo.Service;

import com.sambat.demo.Dto.User.UserResponseDto;
import com.sambat.demo.Entity.UserEntity;
import com.sambat.demo.Mapper.UserMapper;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Dto.User.UserDto;
import com.sambat.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public ResponseEntity<BaseDataResponseModel> getUsers(){
        List<UserEntity> users = userRepository.findAll();
        if(users == null || users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseDataResponseModel("fail", "No Users Found", null));
        }

        List<UserResponseDto> userDtos =  userMapper.userEntityToDtoList(users);

        return ResponseEntity.ok( new BaseDataResponseModel("success", "All Users", userDtos));
    }

    public ResponseEntity<BaseDataResponseModel> getUserById(Long id){
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if(userOpt.isPresent()){
            UserResponseDto userDto = userMapper.userEntityToDto(userOpt.get());
            return ResponseEntity.ok(new BaseDataResponseModel("success", "user found", userDto));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseDataResponseModel("error", "user not found", null));
    }

    public ResponseEntity<BaseResponseModel> addUser(UserDto payload) {
        if (userRepository.existsByName(payload.getName()) || userRepository.existsByEmail(payload.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponseModel("fail", "user already exists"));
        }
        UserEntity user = userMapper.userDtoToEntity(payload);
        userRepository.save(user);

        BaseResponseModel response = new BaseResponseModel("success", "User added");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<BaseResponseModel> deleteUserById(Long id){
        if(!userRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("fail", "user not found"));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(new BaseResponseModel("success", "user deleted"));
    }

    public ResponseEntity<BaseResponseModel> updateUserById(Long id, UserDto payload){
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if(userOpt.isPresent()){
            UserEntity user = userOpt.get();
            userMapper.updateUserEntityFromDto(user, payload);
            userRepository.save(user);
            return ResponseEntity.ok(new BaseResponseModel("success", "user updated"));
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("error", "user not found"));
        }
    }

    public ResponseEntity<BaseDataResponseModel> searchUser(String name) {
        String nameCheck = name != null ? name.toLowerCase() : null;
        List<UserEntity> user = userRepository.findByUserName(nameCheck);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseDataResponseModel("success", "user found", user));
    }
}
