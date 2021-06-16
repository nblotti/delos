package ch.nblotti;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.actuate.metrics.data.MetricsRepositoryMethodInvocationListener;
import org.springframework.boot.actuate.metrics.data.RepositoryTagsProvider;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.Formatter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching
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
    public DateTimeFormatter format1() {
        return DateTimeFormatter.ofPattern(dateFormat);
    }


    @Bean
    public Cache cacheOne() {
        return new CaffeineCache(CACHE_NAME, Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
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

    //Fix for https://github.com/spring-projects/spring-boot/issues/26630
    @Bean
    public static MetricsRepositoryMethodInvocationListener metricsRepositoryMethodInvocationListener(MetricsProperties
                                                                                                              metricsProperties, @Lazy MeterRegistry registry, RepositoryTagsProvider tagsProvider) {
        MetricsProperties.Data.Repository properties = metricsProperties.getData().getRepository();
        return new MetricsRepositoryMethodInvocationListener(registry, tagsProvider, properties.getMetricName(),
                properties.getAutotime());
    }
    //Fix for https://stackoverflow.com/questions/57607445/spring-actuator-jvm-metrics-not-showing-when-globalmethodsecurity-is-enabled/57908577#57908577
    @Bean
    InitializingBean forcePrometheusPostProcessor(BeanPostProcessor meterRegistryPostProcessor, PrometheusMeterRegistry registry) {
        return () -> meterRegistryPostProcessor.postProcessAfterInitialization(registry, "");
    }


}
