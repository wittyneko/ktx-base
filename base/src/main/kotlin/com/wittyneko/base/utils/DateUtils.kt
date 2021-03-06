@file:Suppress("SimpleDateFormat")

package com.wittyneko.base.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import java.util.Calendar.*

/**
 * 日期时间
 * Created by wittyneko on 2017/11/21.
 */

val `yyyy_MM_dd__HH$mm$ss` get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
val `yyyy_MM_dd__HH$mm` get() = SimpleDateFormat("yyyy-MM-dd HH:mm")
val yyyy_MM_dd get() = SimpleDateFormat("yyyy-MM-dd")

val `MM_dd__HH$mm` get() = SimpleDateFormat("MM-dd HH:mm")
val MM_dd get() = SimpleDateFormat("MM-dd")

// 每月最大天数
val Date.maxDayOfMonth
    get() = getInstance().let {
        it.time = this
        it.getActualMaximum(DAY_OF_MONTH)
    }

val Date.dayOfMonth get() = getInstance().let {
    it.time = this
    it[DAY_OF_MONTH]
}

// 月份0开始
val Date.monthOfYear get() = getInstance().let {
    it.time = this
    it[MONTH]
}

// 年份差
fun Date.betweenYears(other: Date) = run {
    val calendar = Calendar.getInstance()
    calendar.time = this
    val currentYear = calendar[YEAR]
    calendar.time = other
    val otherYear = calendar[YEAR]
    (currentYear - otherYear)
}

// 月份差
fun Date.betweenMonths(other: Date) = run {
    val calendar = Calendar.getInstance()
    calendar.time = this
    val currentYear = calendar[YEAR]
    val currentMonth = calendar[MONTH]
    calendar.time = other
    val otherYear = calendar[YEAR]
    val otherMonth = calendar[MONTH]
    (currentYear - otherYear) * 12 + (currentMonth - otherMonth)
}

// 天数差
fun Date.betweenDays(other: Date) = let {
    val dateformat = SimpleDateFormat("yyyy-MM-dd")
    val thisDate = dateformat.parse(dateformat.format(it))
    val otherDate = dateformat.parse(dateformat.format(other))
    val betweenTime = thisDate.time - otherDate.time
//    val dayMillis = 0x7FFFFFFF_F8000000L
//    val betweenTime = (this.time and dayMillis) - (other.time and dayMillis)
    betweenTime / (1000 * 60 * 60 * 24)
}

// 小时差
fun Date.betweenHours(other: Date) = (this.time - other.time) / (1000 * 60 * 60)

// 分钟差
fun Date.betweenMinutes(other: Date) = (this.time - other.time) / (1000 * 60)

// 秒差
fun Date.betweenSeconds(other: Date) = (this.time - other.time) / 1000

// 毫秒差
fun Date.betweenMillis(other: Date) = this.time - other.time

val Date.timeSeconds get() = this.time / 1000