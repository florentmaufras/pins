package com.demo.pins.ui.map

import androidx.annotation.ColorRes
import com.google.android.gms.maps.model.LatLng

interface Pin {
    fun getPinName(): String
    @ColorRes fun getPinColorId(): Int
    fun getPinPosition(): LatLng
}