package com.demo.pins.feed.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Condition(
    val id: String,
    val args: List<String>?
)
