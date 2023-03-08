package com.media.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;
	private String body;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Comment(String name, String email, String body, Post post) {
		super();
		this.name = name;
		this.email = email;
		this.body = body;
		this.post = post;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
