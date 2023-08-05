package com.demo.pins.map

import android.util.Log
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.pins.R
import com.demo.pins.utils.extension.format
import com.demo.pins.utils.extension.setColorFilterATop
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        viewModel: MapViewModel = viewModel()
    ) {
        val montreal = LatLng(45.508888, -73.561668)
        val cameraPositionState = rememberCameraPositionState("position") {
            position = CameraPosition.fromLatLngZoom(montreal, 1f)
        }

        val markers = viewModel.markers.collectAsStateWithLifecycle()
        val error = viewModel.error.collectAsStateWithLifecycle(null)

        val context = LocalContext.current
        val pinDrawable = AppCompatResources.getDrawable(context, R.drawable.pin)

        val modalSheetState = rememberBottomSheetScaffoldState(
            rememberModalBottomSheetState(
                false
            ) { it != SheetValue.PartiallyExpanded }
        )
        val scope = rememberCoroutineScope()
        val currentMarker: MutableState<Marker?> = remember { mutableStateOf(null) }

        MaterialTheme {
            if (error.value != null) {
                Toast.makeText(
                    context,
                    "Something went wrong!",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("ERROR", error.value?.message ?: "")
            }

            BottomSheetScaffold(
                scaffoldState = modalSheetState,
                sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(align = Alignment.Top)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = currentMarker.value?.name ?: ""
                        )
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            text = currentMarker.value?.displayLocalisation() ?: ""
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.weight(1.0F, true),
                                style = MaterialTheme.typography.bodySmall,
                                text = currentMarker.value?.lastUpdate?.format() ?: ""
                            )
                            Row(
                                modifier = Modifier.weight(1.0F, true),
                                horizontalArrangement = Arrangement.End
                            ) {
                                val currentStarCount = currentMarker.value?.starCount ?: 0
                                for (i in 1..5) {
                                    if (currentStarCount >= i) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = "Star"
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.Grade,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        }
                        Text(
                            style = MaterialTheme.typography.bodySmall,
                            text = "${currentMarker.value?.position?.latitude ?: ""}, ${currentMarker.value?.position?.longitude?: ""}"
                        )
                    }
                }
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    markers.value?.map { marker ->
                        pinDrawable?.setColorFilterATop(context.getColor(marker.color))
                        Marker(
                            state = MarkerState(position = marker.position),
                            icon =  pinDrawable?.toBitmap()?.let { bitmap ->
                                BitmapDescriptorFactory.fromBitmap(
                                    bitmap
                                )
                            },
                            title = marker.name,
                            onClick = {
                                currentMarker.value = marker
                                scope.launch { modalSheetState.bottomSheetState.expand() }
                                true
                            }
                        )
                    }
                }
            }
        }
    }
}