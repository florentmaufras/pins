package com.demo.pins.map.viewmodel

import com.demo.pins.redux.Reducer

class MapReducer: Reducer<MapState, MapAction> {
    override fun reduce(state: MapState, action: MapAction): MapState {
        return when (action) {
            is MapAction.Refresh -> {
                return if (state.locations.isNullOrEmpty()) {
                    MapState.Loading(state.locations)
                } else {
                    state
                }
            }
            is MapAction.Loaded -> {
                MapState.Loaded(action.locations)
            }
            is MapAction.Error -> {
                MapState.Error(state.locations, action.throwable)
            }
            is MapAction.PinClicked -> {
                MapState.DisplayBottomSheet(state.locations, action.location)
            }
        }
    }
}