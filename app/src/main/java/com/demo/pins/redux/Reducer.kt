package com.demo.pins.redux

/**
 * We advise to define a sealed interface of your States
 * and a sealed interface of your Actions
 *
 * example:
 * sealed interface MyStates: State
 * sealed interface MyActions: Action
 *
 * class MyReducer: Reducer<MyStates, MyActions>
 */

interface Reducer<S : State, A : Action> {
    fun reduce(state: S, action: A): S
}