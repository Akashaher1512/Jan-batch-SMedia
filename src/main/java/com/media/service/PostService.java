package com.media.service;

import java.util.List;

import com.media.dto.PostDto;
import com.media.dto.PostResponse;

public interface PostService {
	// getPostById
	public PostDto getPostById(Long id);
	
	//getAllPost
	public PostResponse getAllPosts(int pageNumber , int pageSize , String sortBy , String sortDir);
	
	//createPost
	public PostDto createPost(PostDto postDto);

	//updatePost
	public PostDto updatePost(PostDto postDto , Long id);

	//deletePost
	public String deletePost(Long id);
}
