package com.hieuminh.positiondialogfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hieuminh.positiondialogfragment.databinding.ActivityMainBinding
import com.hieuminh.positiondialoglibrary.popup.HorizontalGravity
import com.hieuminh.positiondialoglibrary.popup.PopupAttribute

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvPopup.setOnClickListener {
            val demoFragment = DemoFragment.newInstance()
            demoFragment.show(
                it,
                supportFragmentManager,
                attribute = PopupAttribute(
                    height = 400.dp,
                    horizontalGravity = HorizontalGravity.END,
                    partitionX = 0,
                    partitionY = 0,
                )
            )
        }
    }
}
