package com.wittyneko.base.extenstions

import android.app.ActivityManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.Nullable

val Context.isAppForeground: Boolean
    get() {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        @Suppress("DEPRECATION")
        val tasks = am.getRunningTasks(1)
        if (tasks.isNotEmpty()) {
            val topActivity = tasks[0].topActivity
            if (topActivity.packageName == packageName) {
                return true
            }
        }
        return false
    }

//inline fun <reified T: Activity> Context.intentFor() = Intent(this, T::class.java)
fun Context.color(@ColorRes id: Int, theme: Resources.Theme? = null) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    resources.getColor(id, theme)
} else {
    @Suppress("DEPRECATION")
    resources.getColor(id)
}

fun Resources.color(@ColorRes id: Int, theme: Resources.Theme? = null) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    getColor(id, theme)
} else {
    @Suppress("DEPRECATION")
    getColor(id)
}