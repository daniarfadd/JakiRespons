package com.example.jakirespons.mvvm.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.jakirespons.base.BaseViewModel
import com.example.jakirespons.data.remote.response.ListReportResponseItem
import com.oazisn.moviecatalog.data.remote.ListReportService
import org.koin.java.KoinJavaComponent.inject
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

    override suspend fun loadData() {
        _list.postValue(apiService.getLatest() ?: listOf())
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
}