package com.busticket.booking.lib

import net.bytebuddy.utility.RandomString
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

fun <T: Any> assignObject(output: T, source: Any): T {
    val mapValue = objectToMap(source)
    output.javaClass.kotlin.memberProperties
            .filterIsInstance<KMutableProperty<*>>()
            .forEach { prop ->
                if (mapValue[prop.name] != null)
                    prop.setter.call(output, mapValue[prop.name])
            }
    return output
}

fun objectToMap(source: Any): Map<String, Any?> {
    val result = mutableMapOf<String, Any?>()
    source.javaClass.kotlin.memberProperties.forEach { prop ->
        result[prop.name] = prop.get(source)
    }
    return result
}

fun randomString(length: Int = 10): String {
    return RandomString.make(length)
}