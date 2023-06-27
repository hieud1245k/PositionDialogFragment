package com.hieuminh.positiondialoglibrary.popup

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.addEditTextIfNotExist
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.getScreenWidth
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.getStatusBarHeight
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.getXY
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.isKeyboardVisible
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.setSoftInputAdjustResize
import kotlin.math.max
import kotlin.math.min

internal abstract class PositionDialogFragment<VB : ViewBinding>(private val sourceView: View?) :
    DialogFragment(), InitLayout<VB> {
    private var handler: Handler? = null
    private var width: Int? = null
    private var horizontalGravity: HorizontalGravity? = null
    private var currentPosition: Pair<Int, Int>? = null

    var partition: Int = 0

    var binding: VB? = null
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = getViewBinding()
        }
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = getViewBinding()
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding!!.root)
            .create()

        val inset = InsetDrawable(ColorDrawable(Color.WHITE), 0, 0, 0, 0)
        dialog.window?.setBackgroundDrawable(inset)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        initView()
        initListener()
        addEditTextIfNotExist(view)
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog?.window?.setDecorFitsSystemWindows(false)
        }
        setSoftInputAdjustResize(dialog?.window, binding?.root)
        initViewPosition.run()
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog?.window?.setDecorFitsSystemWindows(true)
        }
        handler?.removeCallbacks(initViewPosition)
    }

    override fun onDestroy() {
        handler = null
        super.onDestroy()
    }

    private val initViewPosition = object : Runnable {
        override fun run() {
            val isKeyBoardVisible = view?.isKeyboardVisible() == true
            sourceView?.let { view ->
                val newPosition = view.getXY()
                if (currentPosition == newPosition && !isKeyBoardVisible) {
                    return@let
                }
                currentPosition = newPosition
                val (sourceX, sourceY) = newPosition
                val window = dialog?.window
                val params = window?.attributes
                window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                params?.x = getPositionX(view, sourceX, width, horizontalGravity)
                params?.y = getPositionY(view, sourceY)
                window?.setGravity(Gravity.TOP or Gravity.START)
                window?.setLayout(width ?: view.width, WindowManager.LayoutParams.WRAP_CONTENT)
            }
            handler?.postDelayed(this, if (isKeyBoardVisible) 100 else 300)
        }
    }

    private fun getPositionX(sourceView: View, sourceX: Int, width: Int?, horizontalGravity: HorizontalGravity?): Int? {
        val screenWidth = getScreenWidth(activity)
        return when {
            horizontalGravity == HorizontalGravity.START -> {
                val startWidth = sourceX + sourceView.width - (width ?: 0) // Distance between left screen and source view
                max(0, startWidth)
            }
            horizontalGravity == HorizontalGravity.END -> {
                min(sourceX, screenWidth)
            }
            width != null -> {
                val endWidth = screenWidth - sourceX - sourceView.width // Distance between source view and right screen
                getPositionX(sourceView, sourceX, width, (if (endWidth < sourceX) HorizontalGravity.START else HorizontalGravity.END))
            }
            else -> {
                sourceX
            }
        }
    }

    private fun getPositionY(sourceView: View, sourceY: Int): Int {
        return sourceY.plus(sourceView.height + DEFAULT_PARTITION + partition).minus(activity.getStatusBarHeight())
    }

    fun setDefaultWidth(width: Int, horizontalGravity: HorizontalGravity? = null) {
        this.width = width
        this.horizontalGravity = horizontalGravity
    }

    companion object {
        private const val DEFAULT_PARTITION = 4
    }
}
