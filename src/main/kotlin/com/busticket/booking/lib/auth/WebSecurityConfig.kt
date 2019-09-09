package com.busticket.booking.lib.auth

import com.busticket.booking.API_PREFIX
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.Filter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true)
class WebSecurityConfig: WebSecurityConfigurerAdapter() {
//    @Autowired
//    private lateinit var authenticationProvider: JwtAuthenticationProvider

    private val _protectedUrls = AntPathRequestMatcher(API_PREFIX)

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean(name = ["jwtAuthenticationFilter"])
    @Throws(Exception::class)
    fun authenticationFilter(): Filter {
        val filter = JwtAuthenticationFilter(AntPathRequestMatcher("$API_PREFIX/**"))
        filter.setAuthenticationManager(authenticationManager())
        return filter
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        return JwtAuthenticationProvider()
    }

    @Bean
    fun forbiddenEntryPoint(): AuthenticationEntryPoint {
        return HttpStatusEntryPoint(HttpStatus.FORBIDDEN)
    }

    override fun configure(http: HttpSecurity) {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter::class.java)
                .authorizeRequests()
                .requestMatchers(_protectedUrls)
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/auth/**", "/upload/**", "/upload")
    }
}