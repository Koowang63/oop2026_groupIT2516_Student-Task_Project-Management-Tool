package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.entity.Comment;
import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findByTaskId(Long taskId);
}