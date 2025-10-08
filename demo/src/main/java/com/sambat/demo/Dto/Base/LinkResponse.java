package com.sambat.demo.Dto.Base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "self", "first", "last", "prev", "next"})
public class LinkResponse {
    private String self;
    private String first;
    private String last;
    private String prev;
    private String next;
}
