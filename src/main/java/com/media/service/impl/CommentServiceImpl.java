package com.media.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.media.dto.CommentDto;
import com.media.entity.Comment;
import com.media.entity.Post;
import com.media.exception.ResourceNotFoundException;
import com.media.exception.SMediaException;
import com.media.repository.CommentRepository;
import com.media.repository.PostRepository;
import com.media.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		// 1. convert commentDto to comment
		Comment comment = commentDtotoComment(commentDto);
		
		// 2. retrive post entity by id
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post_id", postId));
		
		// 3. set post to comment entity
		comment.setPost(post);
		
		// 4. save comment entity to db
		Comment savedComment = commentRepository.save(comment);
		
		// 5. convert comment to commentDto
		CommentDto output = commentToCommentDto(savedComment);
		
		return output;
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// 1. get post by using post id
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post_id", postId) );
		// 2. fetch all comments
		Set<Comment> comments = post.getComments();
		
		// 3. convert List<comment> to List<commentDto>
		List<CommentDto> commentDtolist = comments.stream().map(comment -> commentToCommentDto(comment)).collect(Collectors.toList());
		
		return commentDtolist;
	}

	@Override
	public CommentDto getCommentByCommentId(long postId, long commentId) {
		// 1. find post by using post id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post_id", postId));
		
		// 2. find comment by using comment id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment_id", commentId));
		
		// 3. check whether post and comment belong to each other
		if( (comment.getPost().getId()) != (post.getId())  ) {
			throw new SMediaException(HttpStatus.BAD_REQUEST , "Comment dose not belong to this post");
		}

		// 4. convert comment to commentdto
		CommentDto commentDto = commentToCommentDto(comment);
		
		return commentDto;
	}

	@Override
	public CommentDto updatecomment(long postId, long commentId, CommentDto commentDto) {
		// 1. get post by using post id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post_id", postId));
		
		// 2. get comment by using comment id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment_id", commentId));
		
		// 3. check whether post and comment belong to each other
		if( (comment.getPost().getId()) != (post.getId())  ) {
			throw new SMediaException(HttpStatus.BAD_REQUEST , "Comment dose not belong to this post");
		}
		
		// 4. update comment
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		// 5. save to db
		Comment updatedComment = commentRepository.save(comment);
		
		// 6. convert comment to comment dto
		CommentDto output = commentToCommentDto(updatedComment);
		
		return output;
	}

	@Override
	public String deleteComment(long postId, long commentId) {
		
		//1. get post by using post id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post_id", postId));
		
		// 2. get comment by using comment id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment_id", commentId));
		
		// 3. check wheter the post and comment belong to each other
		if( (comment.getPost().getId()) != (post.getId())  ) {
			throw new SMediaException(HttpStatus.BAD_REQUEST , "Comment dose not belong to this post");
		}
		
		// delete from db
		commentRepository.delete(comment);
		
		return String.format("Comment with comment_id %s deleted sucessfulyy..!!", commentId);
	}
	
	// 1. comment to commentDto
	private CommentDto commentToCommentDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		
		commentDto.setId(comment.getId());
		commentDto.setBody(comment.getBody());
		commentDto.setEmail(comment.getEmail());
		commentDto.setName(comment.getName());
		
		
		return commentDto;
	}
	
	
	//2. CommentDto to Comment
	private Comment commentDtotoComment(CommentDto commentDto) {
		
		Comment comment = new Comment();
		
		comment.setName(commentDto.getName());
		comment.setBody(commentDto.getBody());
		comment.setEmail(commentDto.getEmail());
		
		
		return comment;
		
	}
	
	

}












