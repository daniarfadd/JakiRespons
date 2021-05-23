package com.example.jakirespons.mvvm.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.jakirespons.base.BaseViewModel
import com.example.jakirespons.data.remote.response.ListReportResponseItem
import com.oazisn.moviecatalog.data.remote.ListReportService
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val apiService: ListReportService) : BaseViewModel() {

    lateinit var currentPhotoPath: String
    var storageDir: File? = null

    private val _list = MutableLiveData<List<ListReportResponseItem>>()
    val list : LiveData<List<ListReportResponseItem>> = Transformations.map(_list) {
        it
    }

    var sort = SORT_LATEST
    var search = ""

    override suspend fun loadData() {
        if (search.isEmpty()){
            _list.postValue(apiService.getSort(sort) ?: listOf())
        }
        else {
            _list.postValue(apiService.getSearch(search) ?: listOf())
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    companion object {
        const val SORT_LATEST = "latest"
        const val SORT_OLDEST = "oldest"
        const val SORT_COMMENT = "comment"
        const val SORT_SUPPORT = "support"
    }
}