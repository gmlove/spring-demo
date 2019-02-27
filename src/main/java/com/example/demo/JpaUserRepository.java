package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, Integer> {

  @Query(value = "SELECT * FROM user WHERE name = ?1", nativeQuery = true)
  List<User> findByName(String name);

  @Query(value = "UPDATE user set updated_at=UTC_TIMESTAMP where id=:userId", nativeQuery = true)
  @Modifying
  void updateTime(int userId);

}
