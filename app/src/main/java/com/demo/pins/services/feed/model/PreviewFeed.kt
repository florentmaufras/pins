package com.demo.pins.services.feed.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PreviewFeed(
    @Json(name = "replaced_feeds")
    val replacedFeeds: List<FeedCode>
)