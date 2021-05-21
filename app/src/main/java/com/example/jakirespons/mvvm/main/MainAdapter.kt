package com.example.jakirespons.mvvm.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jakirespons.databinding.ItemsLaporanBinding

class MainAdapter(val onClick: () -> Unit) : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemsLaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 10

    inner class ListViewHolder(private val binding: ItemsLaporanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.root.setOnClickListener {
                onClick()
            }
        }
    }


}