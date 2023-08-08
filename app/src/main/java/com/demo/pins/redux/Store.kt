package com.demo.pins.redux

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Please refer to [Reducer] to generate State and Action
 */
interface Store<S : State, A : Action, R : Reducer<S, A>> {
    val reducer: R
    val state: MutableStateFlow<S>

    fun dispatch(action: A) {
        state.value = this.reducer.reduce(state.value, action)
    }
}