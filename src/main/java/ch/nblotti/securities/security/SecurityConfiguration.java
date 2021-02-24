package ch.nblotti.securities.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


  @Value("${idp.validation.url}")
  public String idpValidationUrl;

  @Bean
  public HttpFirewall defaultHttpFirewall() {
    DefaultHttpFirewall firewall = new DefaultHttpFirewall();
    return firewall;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
    web.httpFirewall(defaultHttpFirewall());
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and()
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/ping").permitAll()
      .antMatchers("/config/*").permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(jwtAuthorizationFilter())
      // .addFilter(new JwtAuthorizationFilter(authenticationManager()))
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser("user")
      .password(passwordEncoder().encode("password"))
      .authorities("ROLE_USER");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

    return source;
  }

  private JwtAuthorizationFilter jwtAuthorizationFilter()  throws Exception{
    return new JwtAuthorizationFilter(authenticationManagerBean(), idpValidationUrl);
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
