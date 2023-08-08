package com.demo.pins.redux
import kotlinx.coroutines.flow.MutableStateFlow

class StoreTest(initialFirstName: String, initialLastName: String) :
    Store<StateTest, ActionTest, ReducerTest> {
    override val reducer: ReducerTest = ReducerTest()
    override val state: MutableStateFlow<StateTest> =
        MutableStateFlow(StateTest(initialFirstName, initialLastName))
}