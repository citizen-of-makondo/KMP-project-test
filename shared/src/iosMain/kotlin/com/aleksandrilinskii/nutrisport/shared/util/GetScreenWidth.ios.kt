package com.aleksandrilinskii.nutrisport.shared.util

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
actual fun getScreenWidth(): Float =
    CGRectGetWidth(UIScreen.mainScreen.bounds).toFloat()
