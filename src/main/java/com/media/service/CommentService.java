package com.media.service;

import java.util.List;

import com.media.dto.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId ,CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentByCommentId(long postId , long commentId);
	
	CommentDto updatecomment(long postId, long commentId , CommentDto commentDto);
	
	String deleteComment(long postId , long commentId);
}
