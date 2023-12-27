package com.myblogp3.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> dto;
    private int pageNum;
    private int pageSize;
    private int totalPages;
    private boolean lastPage;

}
