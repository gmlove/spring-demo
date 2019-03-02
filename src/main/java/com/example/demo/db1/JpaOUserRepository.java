package com.example.demo.db1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaOUserRepository extends OUserRepository, JpaRepository<OUser, Integer> {

  @Query(value = "SELECT * FROM ouser WHERE name = ?1", nativeQuery = true)
  List<OUser> findByName(String name);

  @Query(value = "UPDATE ouser set updated_at=UTC_TIMESTAMP where id=:userId", nativeQuery = true)
  @Modifying
  void updateTime(int userId);

}
