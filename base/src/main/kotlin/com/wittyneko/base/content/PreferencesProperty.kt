package com.wittyneko.base.content

import android.content.SharedPreferences
import com.wittyneko.base.global.GSON
import kotlin.reflect.KProperty

/**
 * SharedPreferences 属性代理
 * Created by wittyneko on 2017/10/30.
 * @author wittyneko
 * @since 2018/8/8
 */
open class CommonProperty<T>(val default: T, val prefs: SharedPreferences) {

    open operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getValue(property.name, default)

    open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putValue(property.name, value)

    protected fun getValue(name: String, default: T): T = with(prefs) {
        val res: Any = when(default) {
            is Int -> getInt(name, default)
            is Long -> getLong(name, default)
            is Float -> getFloat(name, default)
            is Boolean -> getBoolean(name, default)
            is String -> getString(name, default)
            else -> throw IllegalArgumentException()
        }
        @Suppress("UNCHECKED_CAST")
        return res as T
    }

    protected fun putValue(name: String, value: T) = with(prefs.edit()) {
        when(value) {
            is Int -> putInt(name, value)
            is Long -> putLong(name, value)
            is Float -> putFloat(name, value)
            is Boolean -> putBoolean(name, value)
            is String -> putString(name, value)
            else -> throw IllegalArgumentException()
        }.apply()
    }
}

open class JsonProperty<T>(val clazz: Class<T>, val prefs: SharedPreferences, val block: ((T) -> Unit)? = null) {


    open operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getValue(property.name)

    open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putValue(property.name, value)

    protected fun getValue(name: String): T = with(prefs) {
        val default = if (clazz.isAssignableFrom(List::class.java)) "[]" else "{}"
        //val default = ""
        val json = getString(name, default)
        return GSON.fromJson(json, clazz)
    }

    protected fun putValue(name: String, value: T) = with(prefs.edit()) {
        block?.also {
            it(value)
        }
        val json = GSON.toJson(value)
        putString(name, json)
        apply()
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> SharedPreferences.property(default: T) = CommonProperty(default, this)

@Suppress("NOTHING_TO_INLINE")
inline fun <T> SharedPreferences.jsonProperty(clazz: Class<T>, noinline block: ((T) -> Unit)? = null) = JsonProperty(clazz, this, block)