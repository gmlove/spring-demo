package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  MethodValidationTestService methodValidationTestService;

  @RequestMapping(value = "/method-validation", method = RequestMethod.GET)
  @ResponseBody
  public String test_method_validation(@RequestParam("n") int n, @RequestParam("o.n") int o_n) {
    return methodValidationTestService.test_validation(n, new MethodValidationTestService.Obj(o_n));
  }

  @Validated
  @Component
  public static class MethodValidationTestService {

    @Data
    @AllArgsConstructor
    public static class Obj {
      @Min(5)
      private int n;
    }

    public String test_validation(@Min(5) int n, @Valid Obj obj) {
      return String.format("n: %s, o.n: %s", n, obj.n);
    }
  }

}
