package com.demo.pins.feed

import com.demo.pins.feed.model.Feeds
import retrofit2.Response
import retrofit2.http.GET

interface FeedAPI {

    @GET("static/full.json")
    suspend fun getFeeds(): Response<Feeds>
}