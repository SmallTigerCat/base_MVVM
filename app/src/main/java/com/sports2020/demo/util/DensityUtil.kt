@file:JvmName("DensityUtil")

package com.sports2020.demo.util

import android.content.Context

/**
 * 获取屏幕宽度（像素）
 */
fun Context.getScreenWidth(): Int = this.resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度（像素）
 */
fun Context.getScreenHeight(): Int = this.resources.displayMetrics.heightPixels

/**
 * 获取状态栏的高度
 */
fun Context.getStatusBarHeight(): Int {
    var statusBarHeight: Int = 0
    var resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = this.resources.getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Context.dip2px(dpValue: Float): Int =
    ((dpValue * this.resources.displayMetrics.density + 0.5f).toInt())

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Context.px2dip(pxValue: Float): Int =
    ((pxValue / this.resources.displayMetrics.density + 0.5f).toInt())