package com.demo.pins.map

import androidx.annotation.ColorRes
import com.demo.pins.feed.model.Feed
import com.google.android.gms.maps.model.LatLng
import java.util.Date

data class Marker(
    val position: LatLng,
    val name: String,
    val city: String,
    val region: String,
    val country: String,
    val starCount: Int,
    val lastUpdate: Date?,
    @ColorRes val color: Int
) {
    companion object {
        fun buildMarker(feed: Feed): Marker {
            return Marker(
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
}
