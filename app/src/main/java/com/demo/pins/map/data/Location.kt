package com.demo.pins.map.data

import androidx.annotation.ColorRes
import com.demo.pins.services.feed.model.Feed
import com.demo.pins.ui.map.Pin
import com.google.android.gms.maps.model.LatLng
import java.util.Date

data class Location(
    val position: LatLng,
    val name: String,
    val city: String,
    val region: String,
    val country: String,
    val starCount: Int,
    val lastUpdate: Date?,
    @ColorRes val color: Int
): Pin {
    companion object {
        fun buildMarker(feed: Feed): Location {
            return Location(
                feed.bounds?.computePosition() ?: LatLng(0.0, 0.0),
                feed.name ?: "",
                feed.location ?: "",
                feed.subCountryCodes ?: "",
                feed.countryCodes ?: "",
                feed.stars,
                feed.bgtfsUploadedAt,
                CountryColor.getColorFor(feed.countryCodes ?: "")
            )
        }
    }

    override fun getPinName(): String = name

    override fun getPinColorId(): Int = color

    override fun getPinPosition(): LatLng = position
}
