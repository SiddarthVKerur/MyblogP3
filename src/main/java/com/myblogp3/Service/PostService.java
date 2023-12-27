package com.myblogp3.Service;

import com.myblogp3.payload.PostDto;
import com.myblogp3.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto);

    void deletePostById(long id);

    PostDto findPostById(long id);

    PostDto updatePostById(long id, PostDto postDto);

    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
