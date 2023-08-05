package com.demo.pins.feed.model

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bounds(
    @Json(name = "max_lat")
    val maxLat: Double?,
    @Json(name = "max_lon")
    val maxLon: Double?,
    @Json(name = "min_lat")
    val minLat: Double?,
    @Json(name = "min_lon")
    val minLon: Double?
) {

    private fun safeAverage(firstValue: Double?, secondValue: Double?): Double {
        var finalValue = firstValue ?: 0.0
        finalValue += secondValue ?: 0.0
        return if (firstValue != null && secondValue != null) {
            finalValue / 2.0
        } else {
            finalValue
        }
    }

    fun computePosition(): LatLng {
        return LatLng(
            safeAverage(minLat, maxLat),
            safeAverage(minLon, maxLon)
        )
    }
}
