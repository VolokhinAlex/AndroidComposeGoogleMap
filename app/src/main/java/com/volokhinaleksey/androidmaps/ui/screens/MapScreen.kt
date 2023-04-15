package com.volokhinaleksey.androidmaps.ui.screens

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.volokhinaleksey.androidmaps.models.Point
import com.volokhinaleksey.androidmaps.viewmodel.PointViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale


/**
 * Screen for displaying Google maps
 */

@Composable
fun MapScreen(pointViewModel: PointViewModel = koinViewModel()) {
    val points = remember { mutableStateListOf<Point>() }
    val uiSettings = remember { MapUiSettings(myLocationButtonEnabled = true) }
    val properties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    pointViewModel.getPoints().collectAsState(initial = emptyList()).value.let {
        points.addAll(it)
    }
    val defaultLocation = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                setMarker(
                    coroutineScope = coroutineScope,
                    pointViewModel = pointViewModel,
                    context = context,
                    latLng = it
                )
            },
            properties = properties,
            uiSettings = uiSettings
        ) {
            AnimationCarMovement(road, cameraPositionState)
            points.forEach {
                Marker(
                    state = MarkerState(LatLng(it.lat, it.lon)),
                    title = it.title,
                    snippet = it.description
                )
            }
        }
    }
}

fun setMarker(
    coroutineScope: CoroutineScope,
    pointViewModel: PointViewModel,
    context: Context,
    latLng: LatLng
) {
    coroutineScope.launch {
        val addresses = coroutineScope.async(Dispatchers.IO) {
            pointViewModel.getAddressFromLocation(
                geocoder = Geocoder(context, Locale.getDefault()),
                lat = latLng.latitude,
                lon = latLng.longitude
            )
        }.await()
        pointViewModel.addPoint(
            Point(
                lat = latLng.latitude,
                lon = latLng.longitude,
                title = addresses?.locality.orEmpty(),
                description = addresses?.getAddressLine(0).orEmpty()
            )
        )
    }
}
