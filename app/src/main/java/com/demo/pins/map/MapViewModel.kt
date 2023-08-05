package com.demo.pins.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.pins.ServiceProvider
import com.demo.pins.feed.model.Feed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel: ViewModel() {

    private val _markers: MutableStateFlow<List<Marker>?> = MutableStateFlow(listOf())
    val markers: StateFlow<List<Marker>?> = _markers.asStateFlow()

    private val _error: MutableSharedFlow<Exception?> = MutableSharedFlow()
    val error: SharedFlow<Exception?> = _error.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val feedsResponse = ServiceProvider.feed.getFeeds()
                Log.d(
                    this.javaClass.name,
                    "We have some feeds: ${feedsResponse.body()?.feeds?.size}"
                )
                extractMarkerFromFeed(feedsResponse.body()?.feeds)
            } catch(e: Exception) {
                Log.e(
                    this.javaClass.name,
                    "Oups! Something went wrong!"
                )
                _error.emit(e)
            }
        }
    }

    private suspend fun extractMarkerFromFeed(feeds: List<Feed>?) {
        if (feeds.isNullOrEmpty()) {
            _error.emit(Exception("Feed is empty"))
            return
        }

        val markerList: ArrayList<Marker> = arrayListOf()

        feeds.forEach {
            markerList.add(
                Marker.buildMarker(it)
            )
        }
        _markers.value = markerList
    }

    fun displayLocalization(marker: Marker): String = "${marker.city}, ${marker.region}, ${marker.country}"
    fun displayPosition(marker: Marker): String {
        return String.format("%.5f, %.5f", marker.position.latitude, marker.position.longitude)
    }
}