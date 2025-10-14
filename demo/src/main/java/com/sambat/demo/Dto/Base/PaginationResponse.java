package com.sambat.demo.Dto.Base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonPropertyOrder({"content", "pagination"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {
    private List<T> content;
    private Object pagination;

    public static <T> PaginationResponse<T> from(Page<T> page){
        PaginationMetadata metadata = new PaginationMetadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast(),
                page.isFirst(),
                page.hasNext(),
                page.hasPrevious(),
                null
        );

        return new PaginationResponse<>(page.getContent(), metadata);
    }

    public static <T> PaginationResponse<T> from(Page<T> page, String baseUrl){
        LinkResponse links = generateLink(baseUrl, page.getNumber(), page.getSize(), page.getTotalPages());

        PaginationMetadata metadata = new PaginationMetadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast(),
                page.isFirst(),
                page.hasNext(),
                page.hasPrevious(),
                links
        );

        return new PaginationResponse<>(page.getContent(), metadata);
    }

    private static LinkResponse generateLink(String baseUrl, int page, int size, int totalPages){

        LinkResponse links = new LinkResponse();

        // self link
        links.setSelf(createUrl(baseUrl, page, size));

        links.setFirst(createUrl(baseUrl, 0, size));
        links.setLast(createUrl(baseUrl, totalPages - 1, size));
        // first link
        if(page > 0){
            links.setPrev(createUrl(baseUrl, page - 1, size));
        }

        // last link
        if (page < totalPages - 1){
            links.setNext(createUrl(baseUrl, page + 1, size));
        }
        return links;
    }
    private static String createUrl(String baseUrl, int page, int size){
        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?page=").append(page);
        url.append("&size=").append(size);
        return url.toString();
    }
}
