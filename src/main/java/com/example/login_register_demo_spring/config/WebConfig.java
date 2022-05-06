package com.example.login_register_demo_spring.config;

import com.example.login_register_demo_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserService userService;

    @Autowired
    private DataSource datasource;
    @Bean
    public BCryptPasswordEncoder getEncoder(){

        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(getEncoder());
        return auth;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().mvcMatchers("/register/*","/register").permitAll().
                and().authorizeRequests().mvcMatchers("/accountConfirm*","/accountConfirm").permitAll().
                and().authorizeRequests().mvcMatchers("/resetPassword*","/resetPassword").permitAll().
                and().authorizeRequests().mvcMatchers("/resetComplete*","/resetComplete").permitAll().
                and().authorizeRequests().mvcMatchers("/resetPasswordSuccess*","/resetPasswordSuccess").permitAll().

                and().authorizeRequests().anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/",true)
                .permitAll()
                .and().httpBasic()
                .and().rememberMe().key("secretKey").tokenValiditySeconds(10)
                .tokenRepository(tokenRepository())
                .and().logout().logoutSuccessUrl("/login")
              //  .logoutRequestMatcher()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .permitAll()

        ;
        http.csrf().disable();
    }
    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl impl=new JdbcTokenRepositoryImpl();
        impl.setDataSource(datasource);
        return impl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
