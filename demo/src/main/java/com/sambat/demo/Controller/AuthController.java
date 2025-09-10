package com.sambat.demo.Controller;

import com.sambat.demo.Dto.Base.Response;
import com.sambat.demo.Dto.User.UserDto;
import com.sambat.demo.Service.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody UserDto payload){
        String token = authService.register(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("201", "created user", token));
    }
}
