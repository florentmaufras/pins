package com.demo.pins.map.viewmodel

import com.demo.pins.redux.Reducer

class MapReducer: Reducer<MapState, MapAction> {
    override fun reduce(state: MapState, action: MapAction): MapState {
        return when (action) {
            is MapAction.Refresh -> {
                return if (action.locations.isNullOrEmpty()) {
                    MapState.Loading(action.locations)
                } else {
                    state
                }
            }
            is MapAction.Loaded -> {
                MapState.Loaded(action.locations)
            }
            is MapAction.Error -> {
                if (action.locations.isNullOrEmpty()) {
                    MapState.Error(action.locations, action.throwable)
                } else {
                    MapState.RefreshError(action.locations, action.throwable)
                }
            }
        }
    }
}