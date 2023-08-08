package com.demo.pins.services.feed.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeedCode(
    @Json(name = "feed_code")
    val feedCode: String
)
