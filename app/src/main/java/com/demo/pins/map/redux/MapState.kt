package com.demo.pins.map.redux

import com.demo.pins.map.data.Location
import com.demo.pins.redux.State

sealed class MapState(val locations: List<Location>?): State {
    class Loading(locations: List<Location>?): MapState(locations)
    class Error(locations: List<Location>?, val throwable: Throwable): MapState(locations)
    class Loaded(locations: List<Location>?): MapState(locations)
    class DisplayBottomSheet(locations: List<Location>?, val location: Location): MapState(locations)
}