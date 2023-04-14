package com.volokhinaleksey.androidmaps.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.location.Geocoder
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.volokhinaleksey.androidmaps.R
import com.volokhinaleksey.androidmaps.models.Point
import com.volokhinaleksey.androidmaps.viewmodel.PointViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
        position = CameraPosition.fromLatLngZoom(defaultLocation, 14f)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                coroutineScope.launch {
                    val addresses = coroutineScope.async(Dispatchers.IO) {
                        pointViewModel.getAddressFromLocation(
                            geocoder = Geocoder(context, Locale.getDefault()),
                            lat = it.latitude,
                            lon = it.longitude
                        )
                    }.await()
                    pointViewModel.addPoint(
                        Point(
                            lat = it.latitude,
                            lon = it.longitude,
                            title = addresses?.locality.orEmpty(),
                            description = addresses?.getAddressLine(0).orEmpty()
                        )
                    )
                }
            },
            properties = properties,
            uiSettings = uiSettings
        ) {
//            MapEffect { map ->
//                bitmapDescriptorFromVector(context, R.drawable.car)?.let {
//                    setAnimation(
//                        map, road,
//                        it
//                    )
//                }
//            }
            CarMove(road, cameraPositionState)
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

private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(android.graphics.Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

@Composable
private fun CarMove(
    directionPoint: List<LatLng>,
    cameraPositionState: CameraPositionState
) {
    var markerPosition by remember { mutableStateOf(directionPoint[0]) }
    var markerRotation by remember {
        mutableStateOf(
            180f + getCarRotation(
                directionPoint[0],
                directionPoint[1]
            )
        )
    }
    val coroutineScope = rememberCoroutineScope()
    var pointPosition by remember { mutableStateOf(0) }
    var isDriving by remember { mutableStateOf(false) }
    val markerAnimation =
        animateFloatAsState(
            targetValue = 1f, animationSpec = tween(durationMillis = 5000),
            label = ""
        )
    if (isDriving) {
        LaunchedEffect(key1 = true) {
            coroutineScope.launch {
                while (pointPosition < directionPoint.size - 1) {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(directionPoint[pointPosition], 14f),
                        300
                    )
                    markerPosition = LatLng(
                        directionPoint[pointPosition].latitude + (directionPoint[pointPosition + 1].latitude - directionPoint[pointPosition].latitude) * markerAnimation.value,
                        directionPoint[pointPosition].longitude + (directionPoint[pointPosition + 1].longitude - directionPoint[pointPosition].longitude) * markerAnimation.value
                    )
                    markerRotation = 180f + getCarRotation(
                        directionPoint[pointPosition],
                        directionPoint[pointPosition + 1]
                    )
                    delay(300)
                    pointPosition++
                }
            }
        }
    }
    Marker(
        state = MarkerState(markerPosition),
        icon = bitmapDescriptorFromVector(LocalContext.current, R.drawable.car),
        rotation = markerRotation,
        flat = true,
        onClick = {
            isDriving = !isDriving
            isDriving
        }
    )
}


//markerRotation = 180f + getCarRotation(
//                    LatLng(
//                        directionPoint[pointPosition].latitude + (directionPoint[pointPosition + 1].latitude - directionPoint[pointPosition].latitude) * markerAnimation.value,
//                        directionPoint[pointPosition].longitude + (directionPoint[pointPosition + 1].longitude - directionPoint[pointPosition].longitude) * markerAnimation.value
//                    ),
//                    LatLng(
//                        directionPoint[pointPosition + 1].latitude + (directionPoint[pointPosition + 1].latitude - directionPoint[pointPosition].latitude) * markerAnimation.value,
//                        directionPoint[pointPosition + 1].longitude + (directionPoint[pointPosition + 1].longitude - directionPoint[pointPosition].longitude) * markerAnimation.value
//                    )
//                )