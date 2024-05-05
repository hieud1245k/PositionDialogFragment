package com.hieuminh.positiondialogfragment

import android.content.res.Resources

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.dp: Int
    get() = this.toFloat().dp
