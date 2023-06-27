package com.hieuminh.positiondialoglibrary.popup

import androidx.viewbinding.ViewBinding

 interface InitLayout<VB: ViewBinding> {
    fun getViewBinding(): VB
    fun initView()
    fun initListener()
}
