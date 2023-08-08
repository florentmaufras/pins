package com.demo.pins.services.feed

import com.demo.pins.services.feed.model.Feeds
import retrofit2.Response
import retrofit2.http.GET

interface FeedAPI {

    @GET("static/full.json")
    suspend fun getFeeds(): Response<Feeds>
}