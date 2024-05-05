package com.hieuminh.positiondialogfragment

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class DemoAdapter(private val originDataList: List<String>) : RecyclerView.Adapter<DemoAdapter.DemoViewHolder>(), Filterable {
    private var dataList = originDataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
        return DemoViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values = originDataList.filter { it.contains(p0.toString(), true) }
                }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                dataList = p1?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }

    inner class DemoViewHolder(private val textView: TextView) : ViewHolder(textView) {
        fun bind(data: String) {
            textView.text = data
            textView.setPadding(8)
        }
    }
}
