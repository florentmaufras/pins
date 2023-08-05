package com.demo.pins.feed.model

import com.squareup.moshi.Json

data class Bounds(
    @Json(name = "max_lat")
    val maxLat: Float?,
    @Json(name = "max_lon")
    val maxLon: Float?,
    @Json(name = "min_lat")
    val minLat: Float?,
    @Json(name = "min_lon")
    val minLon: Float?
)
