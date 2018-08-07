package com.wittyneko.base

import android.app.Application

object Base {
    var DEBUG = BuildConfig.DEBUG
    lateinit var baseApp: Application
}