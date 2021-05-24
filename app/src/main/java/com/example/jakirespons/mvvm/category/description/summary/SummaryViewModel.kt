package com.example.jakirespons.mvvm.category.description.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jakirespons.base.BaseViewModel
import com.example.jakirespons.data.remote.AddReportService
import com.example.jakirespons.utils.Lapor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class SummaryViewModel(private val apiService: AddReportService) : ViewModel() {
    sealed class Event {
        data class IsLocValid(val bool: Boolean) : Event()
        data class Done(val bool: Boolean) : Event()
        data class ShowSnackbar(val message: String) : Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    var isConnected = false

    fun validate() {
        viewModelScope.launch {
            if (Lapor.latitude == 0.0 && Lapor.longitude == 0.0) {
                eventChannel.send(Event.IsLocValid(false))
            }
            else {
                eventChannel.send(Event.IsLocValid(true))
                //post data
                if (isConnected) {
                    val file = File(Lapor.photoPath)
                    val requestFile = file.asRequestBody()
                    val bodyFile = MultipartBody.Part.createFormData("photo", file.name, requestFile)

                    val result = apiService.insertData(
                        Lapor.description.removeSurrounding("\""),
                        Lapor.category.removeSurrounding("\""),
                        Lapor.longitude,
                        Lapor.latitude,
                        bodyFile
                    )

                    if (result.isNotEmpty()) {
                        eventChannel.send(Event.Done(true))
                        Lapor.clear()
                    }
                    else {
                        eventChannel.send(Event.Done(false))

                    }
                }
                else {
                    eventChannel.send(Event.ShowSnackbar(BaseViewModel.MESSAGE_NO_INTERNET))
                    eventChannel.send(Event.Done(false))
                }
            }
        }
    }

    fun waitLocation() {
        viewModelScope.launch {
            delay(2000)
            validate()
        }
    }
}