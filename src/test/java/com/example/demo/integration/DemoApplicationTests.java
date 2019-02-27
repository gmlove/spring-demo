package com.example.demo.integration;

import com.example.demo.DemoApplication;
import com.example.demo.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(classes = DemoApplication.class)
@Sql(scripts = "classpath:insert-user.sql")
@TestPropertySource("classpath:int-test.yml")
public class DemoApplicationTests {

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
