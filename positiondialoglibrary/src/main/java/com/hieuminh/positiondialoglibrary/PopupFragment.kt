package com.hieuminh.positiondialoglibrary

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hieuminh.positiondialoglibrary.popup.TabletPopupFragment

abstract class PopupFragment : Fragment() {
    fun show(sourceView: View?, fragmentManager: FragmentManager, tag: String? = null) {
        TabletPopupFragment.sourceView = sourceView
        val tabletFragment = TabletPopupFragment.newInstance(true).apply {
            initParentFragment(this@PopupFragment)
        }
        tabletFragment.show(fragmentManager, tag)
    }
}
