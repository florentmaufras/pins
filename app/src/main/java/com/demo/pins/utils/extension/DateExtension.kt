package com.demo.pins.utils.extension

import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun Date.format(style: Int = DateFormat.LONG, locale: Locale = Locale.CANADA_FRENCH): String =
    DateFormat.getDateInstance(style, locale).format(this)
