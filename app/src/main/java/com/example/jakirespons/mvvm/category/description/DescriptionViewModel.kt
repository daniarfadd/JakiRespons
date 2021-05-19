package com.example.jakirespons.mvvm.category.description

import androidx.lifecycle.*
import com.example.jakirespons.utils.Lapor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DescriptionViewModel : ViewModel() {
    sealed class Event {
        data class IsValid(val bool: Boolean) : Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _desc = MutableLiveData<String>()
    val desc : LiveData<String> = Transformations.map(_desc) {
        it
    }

    fun setDesc(desc: String) {
        _desc.value = desc
        Lapor.description = desc
    }

    fun validate(){
        viewModelScope.launch {
            val desc = _desc.value ?: ""
            if (desc.length > 10) {
                eventChannel.send(Event.IsValid(true))
            }
            else {
                eventChannel.send(Event.IsValid(false))
            }
        }
    }
}