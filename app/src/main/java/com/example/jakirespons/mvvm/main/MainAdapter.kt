package com.example.jakirespons.mvvm.main

import android.location.Geocoder
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jakirespons.data.remote.response.ListReportResponseItem
import com.example.jakirespons.databinding.ItemsLaporanBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter(val onClick: (item: ListReportResponseItem) -> Unit) : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {

    private val data = arrayListOf<ListReportResponseItem>()
    fun setData(list: List<ListReportResponseItem>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemsLaporanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ListViewHolder(private val binding: ItemsLaporanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListReportResponseItem) {
            binding.apply {
                root.setOnClickListener {
                    onClick(item)
                }

                Glide.with(root.context)
                    .load(item.photo)
                    .into(imgLaporan)

                tvItemTitle.text = item.title ?: "-"

                val lat = item.lat?.toDouble() ?: 0.0
                val long = item.longi?.toDouble() ?: 0.0

                val geocoder = Geocoder(root.context, Locale.getDefault())
                try {
                    val addr = geocoder.getFromLocation(lat, long, 1)
                    if (addr.size > 0 ){
                        tvLaporanLokasi.text = addr[0].locality
                    }
                    else {
                        tvLaporanLokasi.text = "-"
                    }
                }
                catch (e: Exception) {
                    tvLaporanLokasi.text = "-"
                }

                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                try {
                    val date = sdf.parse(item.createdAt ?: "1970-01-01 00:00:00")
                    tvLaporanWaktu.text = DateUtils.getRelativeTimeSpanString(date!!.time)
                }
                catch (e: ParseException) {
                    e.printStackTrace()
                }

                tvLaporanStatus.text = item.status
            }
        }
    }


}