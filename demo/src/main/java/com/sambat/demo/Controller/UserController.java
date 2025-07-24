package com.sambat.demo.Controller;

import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Dto.User.UserDto;
import com.sambat.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<BaseDataResponseModel> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDataResponseModel> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping()
    public ResponseEntity<BaseResponseModel> addUser(@RequestBody UserDto payload) {
        return userService.addUser(payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteUserById(@PathVariable("id") Long id){
        return userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateUserById(@PathVariable Long id, @RequestBody UserDto payload){
        return userService.updateUserById(id, payload);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseDataResponseModel> searchUser(@RequestParam(value = "name", required = false) String name) {
        return userService.searchUser(name);
    }
}
