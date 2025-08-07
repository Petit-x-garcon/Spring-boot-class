package com.sambat.demo.Mapper;

import com.sambat.demo.Dto.User.ChangePasswordDto;
import com.sambat.demo.Dto.User.UpdateUserDto;
import com.sambat.demo.Dto.User.UserDto;
import com.sambat.demo.Dto.User.UserResponseDto;
import com.sambat.demo.Entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDto userEntityToDto(UserEntity userEntity){
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(userEntity.getId());
        userResponseDto.setName(userEntity.getName());
        userResponseDto.setAge(userEntity.getAge());
        userResponseDto.setAddress(userEntity.getAddress());
        userResponseDto.setPassword(userEntity.getPassword());
        userResponseDto.setRole(userEntity.getRole());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setCreatedAt(userEntity.getCreatedAt());
        userResponseDto.setUpdatedAt(userEntity.getUpdatedAt());

        return userResponseDto;
    }

    public List<UserResponseDto> userEntityToDtoList(List<UserEntity> userEntities){
        return userEntities.stream().map(this::userEntityToDto).collect(Collectors.toList());
    }

    public UserEntity userDtoToEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setName(userDto.getName());
        userEntity.setAge(userDto.getAge());
        userEntity.setAddress(userDto.getAddress());
        userEntity.setRole(userDto.getRole());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());

        return userEntity;
    }

    public void updateUserEntityFromDto(UserEntity userEntity, UpdateUserDto userDto){
        userEntity.setName(userDto.getName());
        userEntity.setAge(userDto.getAge());
        userEntity.setAddress(userDto.getAddress());
        userEntity.setRole(userDto.getRole());
        userEntity.setEmail(userDto.getEmail());
    }

    public void changePassword(UserEntity user, ChangePasswordDto password){
        user.setPassword(password.getPassword());
    }
}
