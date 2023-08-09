package com.demo.pins.map

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.pins.R
import com.demo.pins.map.data.Location
import com.demo.pins.map.redux.MapAction
import com.demo.pins.map.redux.MapState
import com.demo.pins.map.redux.MapStore
import com.demo.pins.ui.RowIconWithText
import com.demo.pins.ui.RowRating
import com.demo.pins.ui.common.Spacing
import com.demo.pins.ui.map.GoogleMap
import com.demo.pins.utils.extension.format
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity() {

    private val montreal = LatLng(45.508888, -73.561668)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }

    @Composable
    fun Screen(
        mapStore: MapStore = viewModel()
    ) {
        val currentMapState = mapStore.state.collectAsStateWithLifecycle()

        MaterialTheme {
            (currentMapState.value as? MapState.Error)?.let { mapState ->
                Toast.makeText(
                    LocalContext.current,
                    "Something went wrong!",
                    Toast.LENGTH_LONG
                ).show()

                Log.e(
                    this@MapActivity::class.simpleName,
                    mapState.throwable.message ?: ""
                )
            }
            DisplayBottomSheetScaffold(currentMapState)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DisplayBottomSheetScaffold(
        currentMapState: State<MapState>,
        mapStore: MapStore = viewModel()
    ) {

        val modalSheetState = rememberBottomSheetScaffoldState(
            rememberModalBottomSheetState(
                false
            ) { it != SheetValue.PartiallyExpanded }
        )
        val currentLocation: MutableState<Location?> = remember { mutableStateOf(null) }
        val scope = rememberCoroutineScope()

        updateBottomSheetState(modalSheetState, currentMapState.value, currentLocation, scope)

        BottomSheetScaffold(
            scaffoldState = modalSheetState,
            sheetShape = RoundedCornerShape(topStart = Spacing.EXTRA_LARGE.value, topEnd = Spacing.EXTRA_LARGE.value),
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.Top)
                        .padding(Spacing.EXTRA_LARGE.value),
                    verticalArrangement = Arrangement.spacedBy(Spacing.SMALL.value)
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = currentLocation.value?.name ?: ""
                    )
                    Text(
                        modifier = Modifier.padding(bottom = Spacing.EXTRA_LARGE.value),
                        style = MaterialTheme.typography.bodyMedium,
                        text = currentLocation.value?.let { mapStore.displayCityRegionCountry(it) } ?: ""
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RowIconWithText(
                            imageVector = Icons.Default.Event,
                            iconContentDescription = "Last update date",
                            text = currentLocation.value?.lastUpdate?.format() ?: "",
                            modifier = Modifier.weight(1.0F, true)
                        )
                        RowRating(
                            rating = currentLocation.value?.starCount ?: 0,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.weight(1.0F, true)
                        )
                    }
                    RowIconWithText(
                        imageVector = ImageVector.vectorResource(R.drawable.pin),
                        iconContentDescription = "Latitude and longitude",
                        text = currentLocation.value?.let { mapStore.displayPosition(it) } ?: ""
                    )
                }
            }
        ) {
            GoogleMap(
                initialCameraPosition = CameraPosition.fromLatLngZoom(montreal, 1.0F),
                pins = currentMapState.value.locations,
                onPinClicked = { _, _, index ->
                    var handled = false
                    currentMapState.value.locations?.let { locations ->
                        if (index < locations.size) {
                            mapStore.dispatch(MapAction.PinClicked(locations[index]))
                            handled = true
                        }
                    }
                    handled
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun updateBottomSheetState(modalSheetState: BottomSheetScaffoldState, mapState: MapState, currentLocation: MutableState<Location?>, scope: CoroutineScope) {
        when(mapState) {
            is MapState.DisplayBottomSheet -> {
                currentLocation.value = mapState.location
                scope.launch { modalSheetState.bottomSheetState.expand() }
            }
            is MapState.Loaded-> {
                currentLocation.value = null
                scope.launch { modalSheetState.bottomSheetState.hide() }
            }
            is MapState.Error -> {
                if (modalSheetState.bottomSheetState.isVisible) {
                    scope.launch { modalSheetState.bottomSheetState.partialExpand() }
                }
            }
            is MapState.Loading -> {
                // Do nothing
            }
        }
    }
}