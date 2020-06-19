package ch.nblotti.securities;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.Formatter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@PropertySource(value = "classpath:override.properties", ignoreResourceNotFound = true)
public class SecuritiesApplication {

  public static final String CACHE_NAME = "cache";

  @Value("${global.date-format}")
  public String dateFormat;


  @Value("${message.date-format}")
  public String messageDateFormat;

  public static void main(String[] args) {
    SpringApplication.run(SecuritiesApplication.class, args);
  }


  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    RestTemplate rt = restTemplateBuilder
      .setConnectTimeout(Duration.ofSeconds(30))
      .setReadTimeout(Duration.ofSeconds(30))
      .build();
    rt.getMessageConverters().add(new StringHttpMessageConverter());
    return rt;
  }


  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public DateTimeFormatter format1() {
    return DateTimeFormatter.ofPattern(dateFormat);
  }

  @Bean
  public DateTimeFormatter formatMessage() {
    return DateTimeFormatter.ofPattern(messageDateFormat);
  }

  @Bean
  public Cache cacheOne() {
    return new CaffeineCache(CACHE_NAME, Caffeine.newBuilder()
      .expireAfterWrite(30, TimeUnit.SECONDS)
      .build());
  }

  @Bean
  public Formatter<LocalDate> localDateFormatter() {

    return new Formatter<LocalDate>() {
      @Override
      public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, format1());
      }

      @Override
      public String print(LocalDate object, Locale locale) {
        return format1().format(object);
      }
    };
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue loaderEvent() {
    return new Queue("loader_event");
  }

  @Bean
  FanoutExchange loaderExchange() {
    return new FanoutExchange("loader_exchange");
  }

  @Bean
  public Binding bindingFanoutExchangeQueueEFanout(FanoutExchange loaderExchange, Queue loaderEvent) {
    return BindingBuilder.bind(loaderEvent).to(loaderExchange);
  }

}
