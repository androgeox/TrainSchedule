package com.georgiy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"com.georgiy.repository"})
@PropertySource( {"classpath:dataBaseConfiguration.properties"})
@EnableTransactionManagement
public class DataConfig {

  @Resource
  private org.springframework.core.env.Environment env;


  @Bean
  @Profile("prod")
  public DataSource prodDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("db_driver"));
    dataSource.setUrl(env.getProperty("db_url_prod"));
    dataSource.setUsername(env.getProperty("db_user"));
    dataSource.setPassword(env.getProperty("db_password"));
    dataSource.setConnectionProperties(connectionProperties());
    return dataSource;
  }

  @Bean
  @Profile("test")
  public DataSource testDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("db_driver"));
    dataSource.setUrl(env.getProperty("db_url_test"));
    dataSource.setUsername(env.getProperty("db_user"));
    dataSource.setPassword(env.getProperty("db_password"));
    dataSource.setConnectionProperties(connectionProperties());
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
    emfb.setDataSource(dataSource);
    emfb.setPackagesToScan(env.getProperty("package_to_scan_model"));
    emfb.setJpaVendorAdapter(jpaVendorAdapter());
    emfb.setJpaPropertyMap(jpaPropertiesMap());
    return emfb;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  private Properties connectionProperties() {
    Properties properties = new Properties();
    properties.put("connection.useUnicode", env.getProperty("connection.useUnicode"));
    properties.put("connection.characterEncoding", env.getProperty("connection.characterEncoding"));
    return properties;
  }

  private Map<String, String> jpaPropertiesMap() {
    Map<String, String> properties = new HashMap<>();
    properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
    properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2dll"));
    properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//    properties.put("hibernate.jdbc.batch_size", "100");
//    properties.put("hibernate.order_inserts", "true");
//    properties.put("hibernate.order_updates", "true");
    //properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
    return properties;
  }
}
