package com.wittyneko.base.extenstions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.wittyneko.base.Base.baseApp
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * LifecycleExt
 * Created by wittyneko on 2018/5/14.
 */

inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (T?) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer(t)
        }
    })
}

inline val LifecycleOwner.context: Context
    get() {
        val context: Context
        when (this) {
            is FragmentActivity -> context = this
            is Fragment -> context = activity ?: baseApp
            else -> context = baseApp
        }
        return context
    }

inline fun <reified V> emptyReference(): Reference<V> = WeakReference<V>(null)

inline val LifecycleOwner.refLifecycleOwner: Reference<LifecycleOwner>
    get() = WeakReference<LifecycleOwner>(this)

inline val Fragment.refActivityLifecycleOwner: Reference<LifecycleOwner>
    get() = WeakReference<LifecycleOwner>(this.activity)