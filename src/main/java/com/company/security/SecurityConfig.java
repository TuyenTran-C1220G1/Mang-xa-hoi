package com.company.security;

import com.company.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((UserDetailsService) userDetailServiceImpl).passwordEncoder(NoOpPasswordEncoder.getInstance());

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception { //permitAll tat ca deu co quyen truy cap
        httpSecurity.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll()
                .and().authorizeRequests().antMatchers("/user**").hasRole("USER")
                .and().authorizeRequests().antMatchers("/admin**").hasRole("ADMIN")
                .and()
                .formLogin().loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                .successHandler(customSuccessHandler)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and().exceptionHandling().accessDeniedPage("/khongcoquyen");
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().and() //
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(1 * 24 * 60 * 60);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl(); // Ta lưu tạm remember me trong memory (RAM). Nếu cần mình có thể lưu trong database
        return memory;
    }

}
