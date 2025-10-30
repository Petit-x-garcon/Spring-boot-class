package com.sambat.demo.Controller;

import com.sambat.demo.Dto.Base.Response;
import com.sambat.demo.Dto.external.JsonPlaceholderCommentDto;
import com.sambat.demo.Service.JsonPlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/mock")
public class JsonPlaceholderController {
    @Autowired
    private JsonPlaceholderService jsonPlaceholderService;

    @GetMapping("/comments")
    public ResponseEntity<Response> getComment(){
        List<JsonPlaceholderCommentDto> result = jsonPlaceholderService.getComment();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","success",result));
    }

    @PostMapping("/comments")
    public  ResponseEntity<Response> postComment(@RequestBody JsonPlaceholderCommentDto payload){
        JsonPlaceholderCommentDto result = jsonPlaceholderService.postComment(payload);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("201","success","success",result));
    }
}
