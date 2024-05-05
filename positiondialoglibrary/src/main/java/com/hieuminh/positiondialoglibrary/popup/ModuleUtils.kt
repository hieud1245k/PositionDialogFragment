package com.hieuminh.positiondialoglibrary.popup

import android.app.Activity
import android.graphics.Insets
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

internal object ModuleUtils {
    fun View.isKeyboardVisible(): Boolean {
        val insets = ViewCompat.getRootWindowInsets(this)
        return insets?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
    }

    fun View.getXY(): Pair<Int, Int> {
        return try {
            val location = IntArray(2)
            this.getLocationOnScreen(location)
            val sourceX = location[0]
            val sourceY = location[1]
            Pair(sourceX, sourceY)
        } catch (e: Exception) {
            Pair(0, 0)
        }
    }

    fun getScreenWidth(activity: Activity?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity?.windowManager?.currentWindowMetrics
            val insets: Insets? = windowMetrics?.windowInsets?.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            (windowMetrics?.bounds?.width() ?: 0) - (insets?.left ?: 0) - (insets?.right ?: 0)
        } else {
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun Activity?.getStatusBarHeight(): Int {
        val rectangle = Rect()
        this?.window?.decorView?.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }
}
