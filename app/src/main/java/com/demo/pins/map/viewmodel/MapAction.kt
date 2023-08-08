package com.demo.pins.map.viewmodel

import com.demo.pins.map.data.Location
import com.demo.pins.redux.Action

sealed interface MapAction: Action {
    class Refresh(val locations: List<Location>?): MapAction
    class Loaded(val locations: List<Location>?): MapAction
    class Error(val locations: List<Location>?, val throwable: Throwable): MapAction
}