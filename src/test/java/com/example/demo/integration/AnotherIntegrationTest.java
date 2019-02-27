package com.example.demo.integration;

import static org.mockito.Mockito.when;

import com.example.demo.User;
import com.example.demo.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AnotherIntegrationTest {

  @MockBean
  UserRepository repository;

  @Test
  public void test_mocked_component() {
    when(repository.findById(5))
        .thenReturn(Optional.of(new User(5, "name", new HashSet<>(), null)));

    Assert.assertEquals(5, repository.findById(5).get().getId().intValue());
  }

}
