package com.example.demo.db;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  List<User> findByName(String name);

  Optional<User> findById(Integer id);

  User save(User user);

  void updateTime(int userId);
}
