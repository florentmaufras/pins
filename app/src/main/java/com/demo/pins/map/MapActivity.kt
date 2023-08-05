package com.demo.pins.map

import android.graphics.PorterDuff
import android.util.Log
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.pins.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            map()
        }
    }

    @Composable
    fun map(
        viewModel: MapViewModel = viewModel()
    ) {
        val montreal = LatLng(45.508888, -73.561668)
        val cameraPositionState = rememberCameraPositionState("position") {
            position = CameraPosition.fromLatLngZoom(montreal, 1f)
        }

        val markers = viewModel.markers.collectAsStateWithLifecycle()
        val error = viewModel.error.collectAsStateWithLifecycle(null)

        val context = LocalContext.current
        val pinDrawable = context.getDrawable(R.drawable.pin)

        MaterialTheme() {
            if (error.value != null) {
                Toast.makeText(
                    context,
                    "Something went wrong!",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("ERROR", error.value?.message ?: "")
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                markers.value?.map {
                    pinDrawable?.setColorFilter(context.getColor(it.color), PorterDuff.Mode.SRC_ATOP)
                    Marker(
                        state = MarkerState(position = it.position),
                        icon =  pinDrawable?.toBitmap()?.let {
                            BitmapDescriptorFactory.fromBitmap(
                                it
                            )
                        },
                    title = it.name,
//                    snippet = "Marker in Montreal"
                    )
                }
            }
        }
    }
}