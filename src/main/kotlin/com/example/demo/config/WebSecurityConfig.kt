package com.example.demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
//@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http
                ?.csrf()?.disable()
                ?.authorizeRequests()
                ?.antMatchers("/api**")?.permitAll()
                ?.antMatchers("/api/**")?.permitAll()
                ?.antMatchers("/register")?.permitAll()
//                ?.antMatchers("/")?.hasAnyRole("ADMIN,USER")
//                ?.anyRequest()?.authenticated()
                ?.and()
                ?.formLogin()
                ?.permitAll()
                ?.and()
                ?.logout()
                ?.permitAll()
    }

    override fun userDetailsService(): UserDetailsService {
        return super.userDetailsService()
    }
}