package com.hieuminh.positiondialoglibrary.popup

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.hieuminh.positiondialoglibrary.databinding.FragmentTabletPopupBinding
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.getStatusBarHeight
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.getXY
import com.hieuminh.positiondialoglibrary.popup.ModuleUtils.isKeyboardVisible
import kotlin.math.max
import kotlin.math.min

internal class PositionDialogFragment : DialogFragment() {
    private var handler: Handler? = null
    private var currentPosition: Pair<Int, Int>? = null

    var attribute: PopupAttribute = PopupAttribute.DEFAULT
    var contentFragment: Fragment? = null
    var sourceView: View? = null

    private val binding: FragmentTabletPopupBinding by lazy {
        FragmentTabletPopupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()

        val inset = InsetDrawable(ColorDrawable(Color.WHITE), 0, 0, 0, 0)
        dialog.window?.setBackgroundDrawable(inset)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        attribute.height?.let { limitedHeight ->
            (binding.flContent.layoutParams as? ConstraintLayout.LayoutParams)?.height = limitedHeight
        }
        contentFragment?.let { contentFragment ->
            childFragmentManager.beginTransaction().replace(binding.flContent.id, contentFragment).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        initViewPosition.run()
    }

    override fun onStop() {
        super.onStop()
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
                params?.x = getPositionX(view, sourceX, attribute.width, attribute.horizontalGravity)
                params?.y = getPositionY(view, sourceY)
                window?.setGravity(Gravity.TOP or Gravity.START)
                window?.setLayout(attribute.width ?: view.width, WindowManager.LayoutParams.WRAP_CONTENT)
            }
            handler?.postDelayed(this, if (isKeyBoardVisible) 100 else 300)
        }
    }

    private fun getPositionX(sourceView: View, sourceX: Int, width: Int?, horizontalGravity: HorizontalGravity?): Int {
        val screenWidth = ModuleUtils.getScreenWidth(activity)
        return attribute.partitionX + when {
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
        return sourceY.plus(sourceView.height + attribute.partitionY).minus(activity.getStatusBarHeight())
    }

    companion object {
        fun newInstance() = PositionDialogFragment()
    }
}
