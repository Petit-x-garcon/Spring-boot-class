package com.sambat.demo.Dto.Base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "page", "size", "total_pages", "total_elements",
        "last", "first", "has_next", "has_previous", "links"})
public class PaginationMetadata {
    private int page;
    private int size;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_elements")
    private long totalElements;
    private boolean last;
    private boolean first;
    @JsonProperty("has_next")
    private boolean hasNext;
    @JsonProperty("has_previous")
    private boolean hasPrevious;
    private LinkResponse links;
}
