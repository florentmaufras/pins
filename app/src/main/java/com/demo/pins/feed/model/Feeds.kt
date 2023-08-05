package com.demo.pins.feed.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Feeds(
    val feeds: List<Feed>
)