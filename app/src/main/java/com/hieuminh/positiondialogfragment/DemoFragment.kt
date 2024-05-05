package com.hieuminh.positiondialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.hieuminh.positiondialogfragment.databinding.FragmentDemoBinding
import com.hieuminh.positiondialoglibrary.PopupFragment

class DemoFragment : PopupFragment() {
    private val binding by lazy {
        FragmentDemoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DemoAdapter(List(100) { "Item $it" })
        binding.recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    companion object {
        fun newInstance() = DemoFragment()
    }
}
