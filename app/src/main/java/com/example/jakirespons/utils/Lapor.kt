package com.example.jakirespons.utils

object Lapor {
    var photoPath = ""
    var category = ""
    var description = ""
    var latitude : Double = 0.0
    var longitude : Double = 0.0

    fun clear() {
        photoPath = ""
        category = ""
        description = ""
        latitude = 0.0
        longitude = 0.0
    }

    fun isEmpty() : Boolean {
        return !(photoPath != "" && category != "" && description != "" && latitude != 0.0 && longitude != 0.0)
    }
}