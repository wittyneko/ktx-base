package com.wittyneko.base.extenstions

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext


/**
 * 协程
 * @author wittyneko
 * @since 2018/8/8
 */

@PublishedApi
internal val POOL = newFixedThreadPoolContext(2 * Runtime.getRuntime().availableProcessors(), "bg")

inline suspend fun <T> run(context: CoroutineContext
                           , noinline block: suspend () -> T
) = kotlinx.coroutines.experimental.run(context, block)

inline fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext
                           , noinline block: suspend CoroutineScope.() -> T
) = kotlinx.coroutines.experimental.runBlocking(context, block)

inline fun launch(
        context: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> Unit
) = kotlinx.coroutines.experimental.launch(context, start, block)

inline fun <T> async(
        context: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> T
) = kotlinx.coroutines.experimental.async(context, start, block)

inline fun <T> bg(crossinline block: () -> T): Deferred<T> = org.jetbrains.anko.coroutines.experimental.bg(block)

inline fun uiLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> Unit
) = launch(UI, start, block)

inline fun <T> uiAsync(noinline block: suspend CoroutineScope.() -> T) = async(UI, block = block)

inline fun <T> bgAsync(noinline block: suspend CoroutineScope.() -> T) = async(POOL, block = block)