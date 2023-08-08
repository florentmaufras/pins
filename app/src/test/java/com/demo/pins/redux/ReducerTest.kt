package com.demo.pins.redux

class ReducerTest : Reducer<StateTest, ActionTest> {
    override fun reduce(state: StateTest, action: ActionTest): StateTest {
        return when (action) {
            is ActionUpdateFirstName -> StateTest(action.firstName, state.lastName)
            is ActionUpdateLastName -> StateTest(state.firstName, action.lastName)
        }
    }
}