package com.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.media.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
