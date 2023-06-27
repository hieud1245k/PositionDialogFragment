package com.hieuminh.positiondialoglibrary.popup

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hieuminh.positiondialoglibrary.databinding.FragmentTabletPopupBinding

internal class TabletPopupFragment : PositionDialogFragment<FragmentTabletPopupBinding>(sourceView) {
    private var fragmentParent: Fragment? = null
    private var isHeightLimited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isHeightLimited = arguments?.getBoolean(IS_HEIGHT_LIMIT) ?: false
    }

    override fun getViewBinding() = FragmentTabletPopupBinding.inflate(this.layoutInflater)

    override fun initListener() = Unit

    override fun initView() {
        fragmentParent?.let {
            childFragmentManager.beginTransaction()
                .add(if (isHeightLimited) binding?.flLimitedLayout?.id ?: 0 else binding?.flFullLayout?.id ?: 0, it)
                .commit()
        }
    }

    override fun onDestroyView() {
        sourceView = null
        fragmentParent = null
        super.onDestroyView()
    }

    fun initParentFragment(fragmentParent: Fragment?) {
        this.fragmentParent = fragmentParent
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        // Fix https://stackoverflow.com/questions/10450348/do-fragments-really-need-an-empty-constructor
        var sourceView: View? = null

        private const val IS_HEIGHT_LIMIT = "IS_HEIGHT_LIMIT"
        fun newInstance(isHeightLimited: Boolean) = TabletPopupFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_HEIGHT_LIMIT, isHeightLimited)
            }
        }
    }
}
