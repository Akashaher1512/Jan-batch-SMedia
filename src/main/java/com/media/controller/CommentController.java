package com.media.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.media.dto.CommentDto;
import com.media.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	// create comment 
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable long postId,@Valid @RequestBody CommentDto commentDto){
		CommentDto output = commentService.createComment(postId, commentDto);
		
		return new ResponseEntity<CommentDto>(output , HttpStatus.CREATED);	
	}
	
	// get comments by using post id
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId){
		List<CommentDto> listOfCommentDto = commentService.getCommentsByPostId(postId);
		
		return new ResponseEntity<List<CommentDto>>( listOfCommentDto , HttpStatus.OK );
	}
	
	// get comment by using comment id
	
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable long postId, @PathVariable long commentId){
		
		CommentDto commentDto = commentService.getCommentByCommentId(postId, commentId);
		
		return new ResponseEntity<CommentDto>( commentDto , HttpStatus.OK );	
	}
	
	// update comment
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable long postId , @PathVariable long commentId , @Valid @RequestBody CommentDto commentDto){
		
		CommentDto updatecomment = commentService.updatecomment(postId, commentId, commentDto);
		
		return new ResponseEntity<CommentDto>( updatecomment , HttpStatus.OK );
		
	}
 	
	// delete comment
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable long postId ,@PathVariable long commentId){
		
		String message = commentService.deleteComment(postId, commentId);
		
		return new ResponseEntity<String>( message , HttpStatus.OK );
		
	}
	
}











