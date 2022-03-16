package com.example.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.androidlesson5databasetask.databinding.ItemSpinnerBinding

class SpinnerAdapter(var list: ArrayList<String>) : BaseAdapter() {
    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getCount(): Int = list.size
    override fun getItem(position: Int): String {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return super.getDropDownView(position, convertView, parent)
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding =
            ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.itemTv.text = list[position]
        if (position == 0) {
            binding.itemTv.setTextColor(Color.GRAY)
        }
        var itemView: View
        itemView = binding.root
        isEnabled(position)
        return itemView
    }
}