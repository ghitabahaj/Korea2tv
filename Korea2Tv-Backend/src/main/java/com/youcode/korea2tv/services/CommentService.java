package com.youcode.korea2tv.services;

import com.youcode.korea2tv.models.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment updatedComment);
    void deleteComment(Long id);

    List<Comment> getAllCommentsByMediaId(Long mediaId);
}
