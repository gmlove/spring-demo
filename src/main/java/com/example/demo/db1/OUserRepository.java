package com.example.demo.db1;

import java.util.List;
import java.util.Optional;

public interface OUserRepository {

  List<OUser> findByName(String name);

  Optional<OUser> findById(Integer id);

  OUser save(OUser user);

  void updateTime(int userId);
}
