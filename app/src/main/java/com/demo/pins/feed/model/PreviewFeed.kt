package com.demo.pins.feed.model

import com.squareup.moshi.Json

data class PreviewFeed(
    @Json(name = "replaced_feeds")
    val replacedFeeds: List<FeedCode>
)

data class FeedCode(
    @Json(name = "feed_code")
    val feedCode: String
)