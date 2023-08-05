package com.demo.pins.utils.parser

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class DateJsonAdapter {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CANADA_FRENCH)

    @ToJson
    fun toJson(date: Date): String {
        return dateFormatter.format(date)
    }

    @FromJson
    fun fromJson(dateStringFormat: String): Date? {
        return dateFormatter.parse(dateStringFormat)
    }
}