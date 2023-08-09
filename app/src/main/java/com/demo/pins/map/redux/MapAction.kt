package com.demo.pins.map.redux

import com.demo.pins.map.data.Location
import com.demo.pins.redux.Action

sealed interface MapAction: Action {
    data object Refresh: MapAction
    class Loaded(val locations: List<Location>?): MapAction
    class Error(val throwable: Throwable): MapAction
    class PinClicked(val location: Location): MapAction
}