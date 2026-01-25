package edu.aitu.oop3.db.service;

import edu.aitu.oop3.db.entity.Comment;
import edu.aitu.oop3.db.repository.CommentRepository;

import java.util.List;

public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Long taskId, Long userId, String text) {
        return commentRepository.save(new Comment(taskId, userId, text));
    }

    public List<Comment> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}
