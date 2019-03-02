package com.example.demo;


import com.example.demo.UserController.Dtos.CreatedUserDto;
import com.example.demo.db1.OUser;
import com.example.demo.db1.OUserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ousers")
@Transactional("db1TransactionManager")
public class OUserController {

  @Autowired
  OUserRepository userRepository;

  public static class Dtos {
    @Data
    @NoArgsConstructor
    public static class CreatedUserDto {
      private Integer id;
      private String name;
      private LocalDateTime updatedAt;
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.POST)
  @ResponseBody
  public CreatedUserDto create(@RequestParam(value="name", defaultValue="user name") String name) {
    OUser user = OUser.builder().name(name).build();
    user = userRepository.save(user);
    CreatedUserDto createdUserDto = new CreatedUserDto();
    BeanUtils.copyProperties(user, createdUserDto);
    return createdUserDto;
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH)
  @ResponseBody
  public OUser update(@PathVariable(value="userId") Integer userId) {
    userRepository.updateTime(userId);
    return userRepository.findById(userId).get();
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  @ResponseBody
  public OUser get(@PathVariable(value="userId") Integer userId) {
    return userRepository.findById(userId).get();
  }

}
