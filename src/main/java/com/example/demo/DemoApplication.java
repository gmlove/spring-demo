package com.example.demo;

import com.example.demo.db.User;
import com.example.demo.db1.OUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Configuration
  @EnableJpaRepositories(basePackages = "com.example.demo.db1", entityManagerFactoryRef = "db1EntityManagerFactory", transactionManagerRef = "db1TransactionManager")
  @EnableTransactionManagement
  public static class Db1Configuration {

    @Bean
    @ConfigurationProperties(prefix = "spring.db1datasource")
    public DataSource db1DataSource() {
      return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
        @Qualifier("db1DataSource") DataSource db1DataSource, Environment env) {
      LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
      factory.setDataSource(db1DataSource);
      factory.setPackagesToScan(ClassUtils.getPackageName(OUser.class));
      factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

      Properties jpaProperties = new Properties();
      jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto", "none"));
      jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql", "false"));
      jpaProperties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
      jpaProperties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());

      factory.setJpaProperties(jpaProperties);

      factory.setPersistenceUnitName("db1");

      return factory;
    }

    @Bean
    public JpaTransactionManager db1TransactionManager(
        @Qualifier("db1EntityManagerFactory") final EntityManagerFactory factory) {
      return new JpaTransactionManager(factory);
    }
  }

  @Configuration
  @EnableJpaRepositories(basePackages = "com.example.demo.db", entityManagerFactoryRef = "dbEntityManagerFactory", transactionManagerRef = "dbTransactionManager")
  @EnableTransactionManagement
  public static class DbConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dbDataSource() {
      return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean dbEntityManagerFactory(
        @Qualifier("dbDataSource") DataSource dbDataSource, Environment env) {
      LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
      factory.setDataSource(dbDataSource);
      factory.setPackagesToScan(ClassUtils.getPackageName(User.class));
      factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

      Properties jpaProperties = new Properties();
      jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto", "none"));
      jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql", "false"));
      jpaProperties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
      jpaProperties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
      factory.setJpaProperties(jpaProperties);

      factory.setPersistenceUnitName("db");

      return factory;
    }

    @Bean
    @Primary
    public JpaTransactionManager dbTransactionManager(
        @Qualifier("dbEntityManagerFactory") final EntityManagerFactory factory) {
      return new JpaTransactionManager(factory);
    }
  }

}

