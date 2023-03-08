package com.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.media.dto.PostDto;
import com.media.dto.PostResponse;
import com.media.service.PostService;
import com.media.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//get post by id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id){
		
		PostDto postDto = postService.getPostById(id);
		
		return new ResponseEntity<PostDto>( postDto , HttpStatus.OK );
	}
	
	// get all posts
	
	@GetMapping()
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber" , defaultValue = AppConstants.DEFAULT_PAGE_NUMBER , required = false) int pageNumber ,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy" , defaultValue = AppConstants.DEFAULT_SORT_BY , required = false)  String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
			){
		
		PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize , sortBy , sortDir);
		
		return new ResponseEntity<>(postResponse , HttpStatus.OK);
	}
	
	
	// create new post
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		
		PostDto output = postService.createPost(postDto);
		
		return new ResponseEntity<PostDto>(output , HttpStatus.CREATED);
	}
	
	// update post
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto , @PathVariable("id") Long id){
		
		PostDto output = postService.updatePost(postDto, id);
		
		return new ResponseEntity<PostDto>( output , HttpStatus.OK);
	}
	
	// delete post
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletepost(@PathVariable("id") Long id){
		
		String msg = postService.deletePost(id);
		
		return new ResponseEntity<String>( msg , HttpStatus.OK);
	}
	
		
}










