package com.wittyneko.base.extenstions

import android.view.LayoutInflater
import android.view.View

val View.layoutInflater get() = LayoutInflater.from(this.context)