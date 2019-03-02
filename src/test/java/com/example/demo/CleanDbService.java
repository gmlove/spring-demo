package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import javax.sql.DataSource;

@Component
public class CleanDbService implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class TableInfo {

    private String tableName;
    private String tableSchema;
  }

  public void cleanDb() {
    Map<String, DataSource> dataSources = applicationContext.getBeansOfType(DataSource.class);
    dataSources.values().forEach(dataSource -> {
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      jdbcTemplate.execute("set foreign_key_checks = 0");
      jdbcTemplate.query("show tables", new BeanPropertyRowMapper<>(TableInfo.class))
          .forEach(tableInfo -> jdbcTemplate.execute("truncate table " + tableInfo.tableName));
      jdbcTemplate.execute("set foreign_key_checks = 1");
    });
  }
}
