package com.example.jakirespons.mvvm.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jakirespons.databinding.ItemsLaporanBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemsLaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ListViewHolder(private val binding: ItemsLaporanBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}