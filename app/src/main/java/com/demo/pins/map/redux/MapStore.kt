package com.demo.pins.map.redux

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.pins.services.feed.model.Feed
import com.demo.pins.map.data.Location
import com.demo.pins.redux.Store
import com.demo.pins.services.ServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MapStore : ViewModel(), Store<MapState, MapAction, MapReducer> {

    override val reducer: MapReducer = MapReducer()

    override val state: MutableStateFlow<MapState> =
        MutableStateFlow(MapState.Loading(null))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val feeds = loadFeeds()
            val locations = extractLocationFromFeed(feeds)
            locations?.let { dispatch(MapAction.Loaded(it)) }
        }
    }

    private suspend fun loadFeeds(): List<Feed>? {
        return try {
            dispatch(MapAction.Refresh)
            val feedsResponse = ServiceProvider.feed.getFeeds()
            feedsResponse.body()?.feeds
        } catch (e: Exception) {
            Log.e(
                this.javaClass.name,
                "Oups! Something went wrong!"
            )
            dispatch(MapAction.Error(e))
            null
        }
    }

    private fun extractLocationFromFeed(feeds: List<Feed>?): List<Location>? {
        if (feeds.isNullOrEmpty()) {
            dispatch(MapAction.Error(Exception("Feed is empty")))
            return null
        }

        val locationList: ArrayList<Location> = arrayListOf()

        feeds.forEach {
            locationList.add(
                Location.buildMarker(it)
            )
        }
        return locationList
    }

    fun displayCityRegionCountry(location: Location): String =
        "${location.city}, ${location.region}, ${location.country}"

    fun displayPosition(location: Location): String {
        return String.format("%.5f, %.5f", location.position.latitude, location.position.longitude)
    }
}