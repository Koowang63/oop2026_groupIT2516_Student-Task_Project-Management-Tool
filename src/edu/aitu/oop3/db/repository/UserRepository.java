package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
}