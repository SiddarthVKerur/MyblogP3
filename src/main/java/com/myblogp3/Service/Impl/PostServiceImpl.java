package com.myblogp3.Service.Impl;

import com.myblogp3.Entity.Post;
import com.myblogp3.Exception.ResourceNotFound;
import com.myblogp3.Repository.PostRepository;
import com.myblogp3.Service.PostService;
import com.myblogp3.payload.PostDto;
import com.myblogp3.payload.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    final private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public void deletePostById(long id) {
        postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + id)
        );
        postRepository.deleteById(id);
    }

    @Override
    public PostDto findPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("post not found with id: " + id)
        );
        return mapToDto(post);

    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("post not found with id: " + id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);

    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo,pageSize, sort);
        Page<Post> postObjectPage = postRepository.findAll(pageable);
        List<Post> postList = postObjectPage.getContent();
        List<PostDto> dtoList = postList.stream().map(i -> mapToDto(i)).collect(Collectors.toList());
        PostResponse response = new PostResponse();
        response.setDto(dtoList);
        response.setPageNum(postObjectPage.getNumber());
        response.setPageSize(postObjectPage.getSize());
        response.setLastPage(postObjectPage.isLast());
        response.setTotalPages(postObjectPage.getTotalPages());
        return response;
    }

    public Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        /*Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/
        return post;

    }

    public PostDto mapToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        /*PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());*/
        return postDto;
    }
}



