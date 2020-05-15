package ch.nblotti.securities;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@PropertySource(value = "classpath:override.properties", ignoreResourceNotFound = true)
public class SecuritiesApplication {

  public static final String CACHE_NAME = "cache";

  public static void main(String[] args) {
    SpringApplication.run(SecuritiesApplication.class, args);
  }


  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
      .setConnectTimeout(Duration.ofSeconds(30))
      .setReadTimeout(Duration.ofSeconds(30))
      .build();
  }

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate rt = new RestTemplate();
    rt.getMessageConverters().add(new StringHttpMessageConverter());
    return rt;

  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public DateTimeFormatter format1() {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd");
  }

  @Bean
  public Cache cacheOne() {
    return new CaffeineCache(CACHE_NAME, Caffeine.newBuilder()
      .expireAfterWrite(30, TimeUnit.SECONDS)
      .build());
  }


}
