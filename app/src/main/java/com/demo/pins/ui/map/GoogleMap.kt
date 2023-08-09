package com.demo.pins.ui.map

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import com.demo.pins.R
import com.demo.pins.utils.extension.setColorFilterATop
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun GoogleMap(
    initialCameraPosition: CameraPosition,
    pins: List<Pin>?,
    onPinClicked: (Marker, Pin, Int) -> Boolean,
    modifier: Modifier = Modifier
) {
    val cameraPositionState = rememberCameraPositionState {
        position = initialCameraPosition
    }

    val pinDrawable = AppCompatResources.getDrawable(LocalContext.current, R.drawable.pin)

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        pins?.mapIndexed { index, pin ->
            pinDrawable?.setColorFilterATop(LocalContext.current.getColor(pin.getPinColorId()))
            Marker(
                state = MarkerState(position = pin.getPinPosition()),
                icon = pinDrawable?.toBitmap()?.let { bitmap ->
                    BitmapDescriptorFactory.fromBitmap(
                        bitmap
                    )
                },
                title = pin.getPinName(),
                onClick = { onPinClicked(it, pin, index) }
            )
        }
    }
}

// Attempted but does not render
@Preview(showBackground = true)
@Composable
private fun Preview() {
    GoogleMap(
        initialCameraPosition = CameraPosition.fromLatLngZoom(LatLng(0.0,0.0), 0.0F),
        pins = listOf(
            object: Pin {
                override fun getPinName(): String = "Pin name"

                override fun getPinColorId(): Int = R.color.other

                override fun getPinPosition(): LatLng = LatLng(0.0,0.0)
            }
        ),
        onPinClicked = { _, _, _ -> true },
        modifier = Modifier.fillMaxSize()
    )
}
