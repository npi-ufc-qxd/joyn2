package br.ufc.npi.joyn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
            .antMatchers("/usuario/cadastrar", "/usuario/starter", "/usuario/recuperarsenha",
            		"/usuario/alterarsenha/**", "/usuario/novasenha", "/usuariorest/csrf-token").permitAll()
            .antMatchers("/api/**").hasRole("USUARIO")
            .anyRequest().authenticated()
            .and()
        .exceptionHandling()
            .accessDeniedPage("/negado")
            .and()
        .formLogin()
            .loginPage("/usuario/logar")
            .usernameParameter("email")
            .passwordParameter("senha")
            .defaultSuccessUrl("/evento/meus_eventos")
            .failureUrl("/usuario/logar?error=1")
            .permitAll()
            .and()
        .logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/usuario/logout"))
            .logoutSuccessUrl("/usuario/logar")
            .invalidateHttpSession(true)
            .permitAll()
            .and().httpBasic();
	}
	
	
	
	
}
