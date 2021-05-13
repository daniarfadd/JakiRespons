package com.example.jakirespons.mvvm.lapor.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jakirespons.databinding.CategoryListBinding

class CategoryAdapter(val onClick : (data : String) -> Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val data = arrayListOf<String>()
    fun setData(categories : List<String>) {
        data.clear()
        data.addAll(categories)
        notifyDataSetChanged()
    }
    inner class ViewHolder(private val binding: CategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.btnCategory.text = data
            binding.btnCategory.setOnClickListener { onClick(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}