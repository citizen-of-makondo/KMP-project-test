package com.aleksandrilinskii.nutrisport.shared.util

actual fun getScreenWidth(): Float =
    android.content.res.Resources.getSystem().displayMetrics.widthPixels.toFloat() /
        android.content.res.Resources.getSystem().displayMetrics.density