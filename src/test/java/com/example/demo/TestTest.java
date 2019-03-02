package com.example.demo;

import com.example.demo.db.Book;
import com.example.demo.db.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.HashSet;

public class TestTest {

  @Test
  public void test_jackson_serialization() throws JsonProcessingException {
    User user = User.builder().id(1).name("n").build();
    Book b1 = Book.builder().id(1).name("b1").owner(User.builder().id(1).name("n").build()).build();
    Book b2 = Book.builder().id(2).name("b2").owner(User.builder().id(1).name("n").build()).build();
    user.setBooks(new HashSet<>(Arrays.asList(b1, b2)));

    String result = new ObjectMapper().writeValueAsString(user);
    System.out.println(result);
  }

  @Test
  public void test_copy_properties() {
    A a = new A(1, 2, new B(3, 4), new C(5));

    A1 a1 = new A1();
    a1.setB(new B1());

    BeanUtils.copyProperties(a, a1);
    // nested property with same name but with different class will not be copied, must copy manually
    BeanUtils.copyProperties(a.getB(), a1.getB());

    Assert.assertEquals(a.a, a1.a);
    // property with different name will not be copied
    Assert.assertEquals(0, a1.a2);
    Assert.assertEquals(3, a1.b.b);
    Assert.assertEquals(0, a1.b.b2);
    // nested property with same name and class will be copied automatically
    Assert.assertEquals(5, a1.c.c);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class A {
    private int a;
    private int a1;
    private B b;
    private C c;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class B {
    private int b;
    private int b1;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class C {
    private int c;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class A1 {
    private int a;
    private int a2;
    private B1 b;
    private C c;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class B1 {
    private int b;
    private int b2;
  }

  enum EnumA {
    A, B
  }

  @Test
  public void test_enum() {
    System.out.println(EnumA.values()[(Integer)0]);
    System.out.println(Long.valueOf(Integer.valueOf(1)));
    System.out.println(EnumA.A.ordinal());
    System.out.println(EnumA.B.ordinal());
  }

}
