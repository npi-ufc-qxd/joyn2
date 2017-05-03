package br.ufc.npi.joyn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProviderJoyn authProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .authorizeRequests()
        	.antMatchers("/css/**", "/js/**", "/images/**", "/plugins/**", "/bootstrap/**", "/less/**").permitAll()
            .antMatchers("/usuario/novo", "/usuario/starter" ).permitAll()
            .antMatchers("/usuario/home").hasRole("USUARIO")
            .anyRequest().authenticated()
            .and()
        .exceptionHandling()
            .accessDeniedPage("/negado")
            .and()
        .formLogin()
            .loginPage("/usuario/logar")
            .usernameParameter("email")
            .passwordParameter("senha")
            .defaultSuccessUrl("/usuario/home")
            .failureUrl("/usuario/logar?error=1")
            .permitAll()
            .and()
        .logout()
            .logoutUrl("/usuario/logout")
            .logoutSuccessUrl("/usuario/logar")
            .invalidateHttpSession(true)
            .permitAll();
	}
	
	
	
	
}
