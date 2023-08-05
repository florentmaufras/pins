package com.demo.pins

import com.demo.pins.feed.FeedAPI
import com.demo.pins.utils.parser.BooleanJsonAdapter
import com.demo.pins.utils.parser.DateJsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ServiceProvider {

    private val moshiConverterFactory: MoshiConverterFactory by lazy {
        val moshi = Moshi.Builder()
            .add(BooleanJsonAdapter())
            .add(DateJsonAdapter())
            .build()
        MoshiConverterFactory.create(moshi)
    }

    val feed: FeedAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-cdn.transitapp.com/v3/feeds/")
            .addConverterFactory(moshiConverterFactory)
            .build()

        retrofit.create(FeedAPI::class.java)
    }
}