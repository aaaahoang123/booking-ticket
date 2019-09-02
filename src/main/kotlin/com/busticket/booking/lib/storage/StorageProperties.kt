package com.busticket.booking.lib.storage

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("storage")
class StorageProperties {
    var location = "upload"
    val acceptExtensions = listOf("jpg", "jpeg", "png", "gif")
}