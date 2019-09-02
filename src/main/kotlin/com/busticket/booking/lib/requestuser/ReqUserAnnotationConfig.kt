package com.busticket.booking.lib.requestuser

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class ReqUserAnnotationConfig : WebMvcConfigurer {
    override fun addArgumentResolvers(
            argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(ReqUserResolver())
    }
}