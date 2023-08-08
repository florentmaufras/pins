package com.demo.pins.redux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ReduxTest {

    @Test
    fun redux_playingTwiceAnActionShouldEndUpInTheSameState() {
        val storeTest = StoreTest(initialFirstName, initialLastName)
        storeTest.dispatch(ActionUpdateFirstName(newValue))
        val pastState = storeTest.state.value
        storeTest.dispatch(ActionUpdateFirstName(newValue))
        val newState = storeTest.state.value

        assertThat(pastState).isEqualTo(newState)
    }

    @Test
    fun redux_playingANewActionShouldChangeTheState() {
        val listOfReachState = arrayListOf<StateTest>()
        val expectedStates = listOf(
            StateTest(initialFirstName, initialLastName),
            StateTest(newValue, initialLastName),
            StateTest(newValue, newValue)
        )

        val storeTest = StoreTest(initialFirstName, initialLastName)
        val job = CoroutineScope(Dispatchers.Unconfined).launch {
            storeTest.state.collect {
                listOfReachState.add(it)
            }
        }
        storeTest.dispatch(ActionUpdateFirstName(newValue))
        storeTest.dispatch(ActionUpdateLastName(newValue))
        job.cancel()

        assertThat(listOfReachState).isEqualTo(expectedStates)
    }

    companion object {
        private const val initialFirstName = "test"
        private const val initialLastName = "testLast"
        private const val newValue = "a"
    }
}