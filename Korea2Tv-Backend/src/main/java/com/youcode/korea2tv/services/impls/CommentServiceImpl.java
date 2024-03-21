package com.youcode.korea2tv.services.impls;

import com.youcode.korea2tv.exception.custom.ResourceNotFoundException;
import com.youcode.korea2tv.models.entity.Comment;
import com.youcode.korea2tv.repositories.CommentRepository;
import com.youcode.korea2tv.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Comment updatedComment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));

        existingComment.setContent(updatedComment.getContent());
        // Update other fields as needed

        return commentRepository.save(existingComment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getAllCommentsByMediaId(Long mediaId) {
        return commentRepository.findByMediaId(mediaId);

    }
}