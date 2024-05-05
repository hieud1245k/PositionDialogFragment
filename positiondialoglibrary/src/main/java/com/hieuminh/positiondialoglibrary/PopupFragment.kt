package com.hieuminh.positiondialoglibrary

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hieuminh.positiondialoglibrary.popup.PopupAttribute
import com.hieuminh.positiondialoglibrary.popup.PositionDialogFragment

abstract class PopupFragment : Fragment() {
    fun show(
        sourceView: View?,
        fragmentManager: FragmentManager,
        tag: String? = null,
        attribute:
        PopupAttribute = PopupAttribute.DEFAULT,
    ) {
        val positionDialogFragment = PositionDialogFragment.newInstance().apply {
            this.contentFragment = this@PopupFragment
            this.sourceView = sourceView
            this.attribute = attribute
        }
        positionDialogFragment.show(fragmentManager, tag)
    }
}
