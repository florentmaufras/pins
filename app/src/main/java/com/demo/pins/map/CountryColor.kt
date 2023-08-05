package com.demo.pins.map

import androidx.annotation.ColorRes
import com.demo.pins.R

enum class CountryColor(private val countryCode: String, @ColorRes val colorId: Int) {
    CA("CA", R.color.ca),
    FR("FR", R.color.fr),
    DE("DE", R.color.de),
    US("US", R.color.us),
    UK("UK", R.color.uk);

    companion object {
        @ColorRes fun getColorFor(countryCode: String): Int {
            return CountryColor.values().firstOrNull {
                it.countryCode.equals(countryCode, true)
            }?.colorId ?: R.color.other
        }
    }
}