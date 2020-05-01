package ch.nblotti.securities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@PropertySource(value = "classpath:override.properties", ignoreResourceNotFound = true)
public class SecuritiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritiesApplication.class, args);
	}


  @Bean
  public RestTemplate restTemplate() {
    RestTemplate rt = new RestTemplate();
    rt.getMessageConverters().add(new StringHttpMessageConverter());
    return rt;

  }

}
