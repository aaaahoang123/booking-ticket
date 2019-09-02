package com.busticket.booking.lib.locale

import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.Locale
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class LocaleConfig : WebMvcConfigurer {

    @Bean("messageSource")
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:locale/messages")
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setUseCodeAsDefaultMessage(true)
        return messageSource
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = HeaderLocaleResolver()
        localeResolver.defaultLocale = Locale.Builder().setLanguage("vi").build()
        return localeResolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val lci = LocaleChangeInterceptor()
        lci.paramName = ""
        return lci
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

//    @Bean("multipartResolver")
//    fun multipartResolver(): CommonsMultipartResolver {
//        val multipartResolver = CommonsMultipartResolver()
//        multipartResolver.setMaxUploadSize((25 * 1024 * 1024).toLong())
//        return multipartResolver
//    }


}