package org.example

import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


fun <T : Any> deepCopy(original: T?): T? {
    original?.run {
        if (this is Number || this is Char || this is String || this is Array<*>) return this;

        val copy: T = when (this) {
            is Collection<*> -> this.map { deepCopy(it) } as T
            is Map<*, *> -> this.entries.associate { (k, v) -> deepCopy(k) to deepCopy(v) } as T
            else -> copyObject(this)
        }

        return copy
    } ?: return null
}

fun <T : Any> copyObject(original: T): T {
    original::class.let { clazz ->
        return clazz.primaryConstructor?.let { constructor ->
            val args = constructor.parameters.associateWith { param ->
                val property = original::class.memberProperties.first { it.name == param.name } as KProperty1<T, Any>
                property.getValueFrom(original).run { deepCopy(this) }
            }

            constructor.callBy(args)
        } ?: clazz.createInstance()
    }
}

/**
 * Retrieves the value from a Kotlin property. If the property is inaccessible (e.g., private final field),
 * falls back to using Java reflection to retrieve the value from the corresponding field.
 */
fun <T : Any> KProperty1<T, Any>.getValueFrom(obj: T) = try {
    this.get(obj)
} catch (e: Exception) {
    obj::class.java.declaredFields.forEach { field ->
        if (field.name == this.name) {
            field.isAccessible = true
            return field.get(obj)
        }
    }
}