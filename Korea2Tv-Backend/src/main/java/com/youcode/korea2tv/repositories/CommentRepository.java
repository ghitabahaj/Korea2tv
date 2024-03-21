package com.youcode.korea2tv.repositories;

import com.youcode.korea2tv.models.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Add custom methods if needed
}