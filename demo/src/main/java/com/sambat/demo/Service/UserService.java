package com.sambat.demo.Service;

import com.sambat.demo.Entity.UserEntity;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Model.UserModel;
import com.sambat.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<BaseDataResponseModel> getUsers(){
        List<UserEntity> users = userRepository.findAll();
        return ResponseEntity.ok( new BaseDataResponseModel("success", "All Users", users));
    }

    public ResponseEntity<BaseDataResponseModel> getUserById(Long id){
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if(userOpt.isPresent()){
            UserEntity user = userOpt.get();
            return ResponseEntity.ok(new BaseDataResponseModel("success", "user found", List.of(user)));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseDataResponseModel("error", "user not found", List.of()));
        }

    }

    public ResponseEntity<BaseResponseModel> addUser(UserModel payload) {
        UserEntity user = new UserEntity();
        user.setName(payload.getName());
        user.setAge(payload.getAge());
        user.setAddress(payload.getAddress());
        user.setRole(payload.getRole());
        user.setEmail(payload.getEmail());
        user.setCreatedAt(LocalDateTime.now());

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

    public ResponseEntity<BaseResponseModel> updateUserById(Long id, UserModel payload){
        Optional<UserEntity> userOpt = userRepository.findById(id);

        if(userOpt.isPresent()){
            UserEntity user = userOpt.get();
            user.setName(payload.getName());
            user.setAge(payload.getAge());
            user.setAddress(payload.getAddress());
            user.setRole(payload.getRole());
            user.setEmail(payload.getEmail());

            userRepository.save(user);

            return ResponseEntity.ok(new BaseResponseModel("success", "user updated"));
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("error", "user not found"));
        }
    }

    public ResponseEntity<BaseDataResponseModel> searchUser(String name) {
        String nameCheck = name != null ? name.toLowerCase() : null;
        UserEntity user = userRepository.findByUserName(nameCheck);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseDataResponseModel("success", "user found", user));
    }
}
