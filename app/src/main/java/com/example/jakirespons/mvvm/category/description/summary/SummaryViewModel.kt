package com.example.jakirespons.mvvm.category.description.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jakirespons.utils.Lapor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SummaryViewModel : ViewModel() {
    sealed class Event {
        data class IsLocValid(val bool: Boolean) : Event()
        data class Done(val bool: Boolean) : Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun validate() {
        viewModelScope.launch {
            if (Lapor.latitude == 0.0 && Lapor.longitude == 0.0) {
                eventChannel.send(Event.IsLocValid(false))
            }
            else {
                eventChannel.send(Event.IsLocValid(true))
                //post data
                delay(5000)
                Lapor.clear()
                eventChannel.send(Event.Done(true))

                // post data then clear Lapor data
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