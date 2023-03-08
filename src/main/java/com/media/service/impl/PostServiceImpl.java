package com.media.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.media.dto.PostDto;
import com.media.dto.PostResponse;
import com.media.entity.Post;
import com.media.exception.ResourceNotFoundException;
import com.media.repository.PostRepository;
import com.media.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	// get post by id
	@Override
	public PostDto getPostById(Long id) {
		// 1. get post from db
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "PostID", id));

		// 2. create PostDto object to return it
		// PostDto postDto = new PostDto();

		// 3. Transfer values from post to postDto
		PostDto postDto = postToPostDto(post);
		/*
		 * postDto.setId(post.getId()); postDto.setTitle(post.getTitle());
		 * postDto.setContent(post.getContent());
		 * postDto.setDescription(post.getDescription());
		 */

		return postDto;
	}

	// get all post
	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize , String sortBy , String sortDir) {
		
		Sort sort = null;
		
		if(sortDir.equalsIgnoreCase("asc") == true) {
			sort = sort.by(sortBy).ascending();
			
		}else if(sortDir.equalsIgnoreCase("desc") == true) {
			sort = sort.by(sortBy).descending();
		}
		else {
			sort = sort.by(sortBy).ascending();
		}
		
		// create pageable object
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);		
		// call findAll method of PaginationAndSorting interface
		Page<Post> page = postRepository.findAll(pageable);
		
		// get content from Page object
		List<Post> postList = page.getContent();
		
		// covert List<Post> into List<PostDto>
		List<PostDto> postDtoList = postList.stream().map((post)-> postToPostDto(post)).collect(Collectors.toList());
		
		// create object of PostResponse
		PostResponse postResponse = new PostResponse();
		// insert values into PostResponse object
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLast(page.isLast());
		
		// return PostResponse
		
		return postResponse;
	}

	// create new post
	@Override
	public PostDto createPost(PostDto postDto) {
		
		Post post = postDtoToPost(postDto);
		
		Post savedPost = postRepository.save(post);
		
		PostDto output = postToPostDto(savedPost);
		
		return output;
	}

	// update existing post
	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		
		//find post with given id
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Post_id", id) );
		
		//update that post for given values
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		
		// save  post into repo
		Post updatedPost = postRepository.save(post);
		
		// return updated post dto
		PostDto output = postToPostDto(updatedPost);
		
		return output;
	}

	// delete post
	@Override
	public String deletePost(Long id) {
		
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Post_Id", id));
		
		postRepository.delete(post);
		
		return String.format("Post with id := %s deleted successfully..!", id);
	}

	// post to postDto conversion
	private PostDto postToPostDto(Post post) {

		PostDto postDto = modelMapper.map(post, PostDto.class);
		
		return postDto;
	}

	// postDto to post conversion
	private Post postDtoToPost(PostDto postDto) { 

		Post post = modelMapper.map(postDto, Post.class);

		return post;
	}

	

}
