package com.demo.pins.redux

sealed class ActionTest : Action

data class ActionUpdateFirstName(val firstName: String) : ActionTest()
data class ActionUpdateLastName(val lastName: String) : ActionTest()