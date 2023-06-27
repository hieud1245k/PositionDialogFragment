package com.hieuminh.positiondialoglibrary.popup

import android.app.Activity
import android.graphics.Insets
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.*
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

 object ModuleUtils {
    fun Fragment.addEditTextIfNotExist(view: View) {
        var hasEdit = false
        val viewGroup = view as? ViewGroup ?: return
        for (i in 0 until view.childCount) {
            val innerView: View = view.getChildAt(i)
            if (innerView is EditText) {
                hasEdit = true
                break
            }
        }
        if (!hasEdit) {
            val edt = EditText(context).apply {
                visibility = View.GONE
                clearFocus()
            }
            viewGroup.removeView(edt)
            viewGroup.addView(edt)
        }
    }

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

     fun setSoftInputAdjustResize(window: Window?, rootView: View?) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
             rootView?.setOnApplyWindowInsetsListener { _, windowInsets ->
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                     val imeHeight = windowInsets.getInsets(WindowInsets.Type.ime()).bottom
                     rootView.setPadding(0, 0, 0, imeHeight)
                     val insets = windowInsets.getInsets(WindowInsets.Type.ime() or WindowInsets.Type.systemGestures())
                     insets
                 }
                 windowInsets
             }
         } else {
             window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
         }
     }
}
