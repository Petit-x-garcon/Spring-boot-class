package com.sambat.demo.Service;

import com.sambat.demo.Common.config.ApplicationConfiguration;
import com.sambat.demo.Common.wrapper.WebClientWrapper;
import com.sambat.demo.Dto.external.JsonPlaceholderCommentDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonPlaceholderService {
    @Autowired
    private WebClientWrapper webClientWrapper;
    @Autowired
    private ApplicationConfiguration appConfig;

    private String BASE_URL;
    private String COMMENTS_URI;

    @PostConstruct
    public void init(){
        this.BASE_URL = appConfig.getJsonPlaceholder().getBaseUrl();
        this.COMMENTS_URI = appConfig.getJsonPlaceholder().getComment();
    }

    public List<JsonPlaceholderCommentDto> getComment(){
        String url = this.BASE_URL.concat(this.COMMENTS_URI);
        return (List<JsonPlaceholderCommentDto>) webClientWrapper.getSyncComment(url, List.class);
    }

    public JsonPlaceholderCommentDto postComment(JsonPlaceholderCommentDto payload){
        String url = this.BASE_URL.concat(this.COMMENTS_URI);
        return webClientWrapper.postSyncComment(url, JsonPlaceholderCommentDto.class, payload);
    }
}
