package com.wittyneko.base.arch

/**
 * 初始化接口
 * Created by wittyneko on 2018/5/15.
 */
interface IInitialize {
    fun initData()
    fun initView()
    fun initEvent()

    fun builderInit(){
        initData()
        initView()
        initEvent()
    }
}

interface IInitView {
    fun initView()
    fun initEvent()

    fun builderInit() {
        initView()
        initEvent()
    }
}