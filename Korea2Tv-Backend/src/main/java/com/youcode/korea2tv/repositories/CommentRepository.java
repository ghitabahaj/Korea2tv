package com.youcode.korea2tv.repositories;

import com.youcode.korea2tv.models.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByMediaId(Long mediaId);
}