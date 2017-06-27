package br.ufc.npi.joyn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.ufc.npi.joyn.config.JwtFilter;

@SpringBootApplication
@Configuration
public class JoynApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoynApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean someFilterRegistration() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(new JwtFilter());
	    registration.addUrlPatterns("/api/*");
	    registration.setName("jwtFilter");
	    registration.setOrder(Integer.MIN_VALUE);
	    return registration;
	}
}
