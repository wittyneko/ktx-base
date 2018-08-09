package com.wittyneko.base.arch

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import com.wittyneko.base.Base.baseApp
import com.wittyneko.base.extenstions.context
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * BasePresenter
 * Created by wittyneko on 2018/5/12.
 */
interface BasePresenter<T : BaseView> : DefaultLifecycleObserver {
    var refViews: Reference<T>

    val views: T?
        get() = refViews.get()

    val safeView: T get() = views!!

    val isViewAttached: Boolean
        get() = views != null

    val context: Context get() = views?.context ?: baseApp

    override fun onCreate(owner: LifecycleOwner)

    override fun onStart(owner: LifecycleOwner) {
    }

    override fun onResume(owner: LifecycleOwner) {
    }

    override fun onPause(owner: LifecycleOwner) {
    }

    override fun onStop(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
        refViews.clear()
    }

}

inline fun <reified V> attachView(owner: LifecycleOwner?): Reference<V> = run {
    val view = owner as? V
    WeakReference<V>(view)
}