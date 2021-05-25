package com.example.jakirespons.mvvm.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.jakirespons.base.BaseViewModel
import com.example.jakirespons.data.remote.response.DetailReportResponseItem
import com.example.jakirespons.data.remote.DetailReportService
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailViewModel(private val apiService: DetailReportService) : BaseViewModel() {
    var id = ""

    private val _data = MutableLiveData<DetailReportResponseItem>()
    val data : LiveData<DetailReportResponseItem> = Transformations.map(_data) {
        it
    }

    override suspend fun loadData() {
        val result = apiService.getDetail(id)?.get(0) ?: DetailReportResponseItem()

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val sdfTo = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        try {
            var date = sdf.parse(result.createdAt ?: "1970-01-01 00:00:00")
            result.createdAt = sdfTo.format(date!!)

            date = sdf.parse(result.currentStatus?.get(0)?.createdAt ?: "1970-01-01 00:00:00")
            result.currentStatus?.get(0)?.createdAt = sdfTo.format(date!!)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        _data.postValue(result)
    }
}