package com.example.jakirespons.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    sealed class Event {
        data class Refresh(val bool: Boolean) : Event()
        data class ShowSnackbar(val message: String) : Event()
    }

    var isConnected = false

    protected val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    abstract suspend fun loadData()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(Event.Refresh(true))
            if (isConnected) {
                loadData()
            } else {
                eventChannel.send(Event.ShowSnackbar(MESSAGE_NO_INTERNET))
            }
            eventChannel.send(Event.Refresh(false))
        }
    }


    companion object {
        const val MESSAGE_NO_INTERNET = "0"
    }

}