package com.wittyneko.base.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.SystemClock.sleep
import android.util.Log
import com.wittyneko.base.Base
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

/**
 * 线程管理
 * Created by wittyneko on 2017/10/31.
 */

/**
 * 主线程运行
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
fun runOnUi(runnable: () -> Unit) = if (isMainThread) runnable() else mainHandler.post(runnable)

fun runOnUi(delayMillis: Long, runnable: () -> Unit) = mainHandler.postDelayed(runnable, delayMillis)

/**
 * 子线程运行
 */
fun runOnBackground(runnable: () -> Unit) = cachedPool.execute(runnable)

fun runOnBackground(delayMillis: Long, runnable: () -> Unit) = cachedPool.execute {
    sleep(delayMillis)
    runnable()
}

/**
 * 仅核心线程池
 * 核心线程池不会回收
 */
val fixedPool by lazy { Executors.newFixedThreadPool(coreSize) as ThreadPoolExecutor }

/**
 * 仅非核心线程池MAX 60秒后回收
 */
val cachedPool by lazy { Executors.newCachedThreadPool() as ThreadPoolExecutor; }

/**
 * 核心线程池 + 非核心线程池MAX 立即回收
 */
val scheduledPool by lazy { Executors.newScheduledThreadPool(coreSize) as ThreadPoolExecutor }

/**
 * 单线程池 顺序执行
 */
val singlePool by lazy { Executors.newSingleThreadExecutor() as ThreadPoolExecutor }

/**
 * 主线程
 */
val mainHandler by lazy { Handler(Looper.getMainLooper()) }

/**
 * 主线程判断
 */
val isMainThread = Looper.myLooper() == Looper.getMainLooper()

val coreSize = run {
    var cores = cpuCoresNumber
    if (cores > 5) cores / 2 else cores
}



open class CrashHandler<T>(private val context: Context, private val clazz: Class<T>): Thread.UncaughtExceptionHandler{
    val defHandler = Thread.getDefaultUncaughtExceptionHandler()
    var delay = 100
    var reStart = !Base.DEBUG
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (handleException(e)) {
            if (reStart) {
                val intent = Intent(context, clazz).putExtra("crash", true).clearTops()
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                am.set(AlarmManager.RTC, System.currentTimeMillis() + delay, pendingIntent)
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
            } else {
                defHandler.uncaughtException(t, e)
            }
        } else {
            defHandler.uncaughtException(t, e)
        }
    }

    open fun handleException(e: Throwable?): Boolean {
        if (e == null)
            return false
        //runOnUi { context.toast("很抱歉,程序出现异常即将退出") }
        return true
    }
}

open class CarshLogHandler: Thread.UncaughtExceptionHandler {
    val defHandler = Thread.getDefaultUncaughtExceptionHandler()
    override fun uncaughtException(t: Thread, e: Throwable?) {
        Log.e(TAG, "thread: ${t.name}", e)
        defHandler.uncaughtException(t, e)
    }
}