package com.demo.pins

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoMap()
        }
    }

    override fun onStart() {
        super.onStart()
        // To move in VM at init + stateFlow
        lifecycleScope.launch(Dispatchers.IO) {
            val feedsResponse = ServiceProvider.feed.getFeeds()
            Log.d("MainActivity", "We have some feeds: ${feedsResponse.body()?.feeds?.size}")
        }
    }

    @Composable
    fun DemoMap() {
        val singapore = LatLng(1.35, 103.87)
        val cameraPositionState = rememberCameraPositionState("position") {
            position = CameraPosition.fromLatLngZoom(singapore, 10f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}