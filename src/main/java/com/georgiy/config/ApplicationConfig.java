package com.georgiy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.georgiy")
@Import(value = {DataConfig.class})
//@EnableScheduling
public class ApplicationConfig extends WebMvcConfigurerAdapter {

  @Bean
  public InternalResourceViewResolver setupViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/pages/");
    resolver.setSuffix(".jsp");
    resolver.setViewClass(JstlView.class);
    return resolver;
  }

//  @Bean
//  @Profile("prod")
//  public static PropertyPlaceholderConfigurer propertyProdPlaceholderConfigurer() {
//    PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
//    configurer.setLocation(new ClassPathResource("applicationProd.properties"));
//    configurer.setIgnoreResourceNotFound(true);
//    return configurer;
//  }

//  @Bean
////  @Profile("test")
//  public static PropertyPlaceholderConfigurer propertyTestPlaceholderConfigurer() {
//    PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
//    configurer.setLocation(new ClassPathResource("applicationTest.properties"));
//    configurer.setIgnoreResourceNotFound(true);
//    return configurer;
//  }

  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver commonsMultipartResolver() {
    CommonsMultipartResolver cmps = new CommonsMultipartResolver();
    cmps.setMaxInMemorySize(50000);
    return cmps;
  }

//  @Bean
//  public AutowireHelper getAutowireHelper() {
//    return new AutowireHelper();
//  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
    resourceHandlerRegistry.addResourceHandler("/WEB-INF/pages/**").addResourceLocations("/pages/");
  }

//  @Bean
//  public HtmlEmail createHtmlEmail() {
//    HtmlEmail email = new HtmlEmail();
//    email.setSmtpPort(25);
//    email.setHostName("ns.szgmu.ru");
//    try {
//      email.setFrom("noreply@ns.szgmu.ru");
//    } catch (EmailException e) {
//      e.printStackTrace();
//    }
//    return email;
//  }

  @Bean
  public TaskScheduler taskScheduler() {
    return new ThreadPoolTaskScheduler();
  }

  @Bean
  public StringHttpMessageConverter stringHttpMessageConverter() {
    StringHttpMessageConverter shmc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    shmc.supports(MediaType.TEXT_PLAIN.getClass());
    shmc.setWriteAcceptCharset(true);
    return shmc;
  }

  private MappingJackson2HttpMessageConverter jacksonMessageConverter() {
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Hibernate4Module().disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION));
    messageConverter.setObjectMapper(mapper);
    return messageConverter;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter jmc = new MappingJackson2HttpMessageConverter();
    jmc.setPrettyPrint(true);
    converters.add(jacksonMessageConverter());

    Jaxb2RootElementHttpMessageConverter j2 = new Jaxb2RootElementHttpMessageConverter();
    converters.add(j2);

    FormHttpMessageConverter ftmc = new FormHttpMessageConverter();
    ftmc.setCharset(Charset.forName("UTF-8"));
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(true).ignoreAcceptHeader(true)
        .useJaf(false)
        .mediaType("form", MediaType.APPLICATION_FORM_URLENCODED)
        .mediaType("text", MediaType.TEXT_PLAIN)
        .mediaType("textxml", MediaType.TEXT_XML)
        .mediaType("xml", MediaType.APPLICATION_XML)
        .mediaType("form-data", MediaType.MULTIPART_FORM_DATA)
        .mediaType("json", MediaType.APPLICATION_JSON);
  }
}