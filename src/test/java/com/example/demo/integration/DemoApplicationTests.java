package com.example.demo.integration;

import com.example.demo.CleanDbService;
import com.example.demo.DemoApplication;
import com.example.demo.db.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class, CleanDbService.class})
@Sql(scripts = "classpath:insert-user.sql",
    config = @SqlConfig(dataSource = "dbDataSource", transactionManager = "dbTransactionManager"))
public class DemoApplicationTests {

  @Autowired
  private CleanDbService cleanDbService;

  @After
  public void setUpCleanDb() {
    cleanDbService.cleanDb();
  }


  @Autowired
  UserRepository repository;

  @Test
  public void test_h2() {
    Assert.assertEquals(2, repository.findById(2).get().getId().intValue());
  }

  @Test
  public void test_extract_out_jpa() {
    Assert.assertEquals(0, repository.findByName("123").size());
  }

}
