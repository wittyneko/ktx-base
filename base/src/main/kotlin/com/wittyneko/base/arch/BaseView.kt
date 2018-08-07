package com.wittyneko.base.arch

import android.arch.lifecycle.LifecycleOwner

/**
 * BaseView
 * Created by Administrator on 2018/5/12.
 */
interface BaseView : LifecycleOwner {


    fun showToast(msg: CharSequence)

    fun showLoading()

    fun dismissLoading()
}