@file:Suppress("NOTHING_TO_INLINE")

package com.wittyneko.myapplication

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.*
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * 协程
 * @author wittyneko
 * @since 2018/8/8
 */

@PublishedApi
internal val POOL = newFixedThreadPoolContext(2 * Runtime.getRuntime().availableProcessors(), "bg")
val UI = Handler(Looper.getMainLooper()).asCoroutineDispatcher("UI")


inline suspend fun <T> run(
        context: CoroutineContext
        , noinline block: suspend () -> T
) = withContext(context) { block() }

inline fun <T> bg(crossinline block: () -> T): Deferred<T> = bgAsync { block() }

inline fun <T> runBlocking(
        context: CoroutineContext = EmptyCoroutineContext
        , noinline block: suspend CoroutineScope.() -> T
) = kotlinx.coroutines.runBlocking(context, block)

inline fun launch(
        context: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> Unit
) = GlobalScope.launch(context, start, block)

inline fun <T> async(
        context: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> T
) = GlobalScope.async(context, start, block)

inline fun uiLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> Unit
) = launch(UI, start, block)

inline fun <T> uiAsync(noinline block: suspend CoroutineScope.() -> T) = async(Dispatchers.Main, block = block)

inline fun <T> bgAsync(noinline block: suspend CoroutineScope.() -> T) = async(POOL, block = block)