package com.volokhinaleksey.androidmaps.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.volokhinaleksey.androidmaps.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimationCarMovement(
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
                        CameraUpdateFactory.newLatLngZoom(directionPoint[pointPosition], 11f),
                        300
                    )
                    markerPosition = LatLng(
                        directionPoint[pointPosition].latitude + (directionPoint[pointPosition + 1].latitude - directionPoint[pointPosition].latitude) * markerAnimation.value,
                        directionPoint[pointPosition].longitude + (directionPoint[pointPosition + 1].longitude - directionPoint[pointPosition].longitude) * markerAnimation.value
                    )
                    markerRotation = 180f + getCarRotation(
                        markerPosition,
                        directionPoint[pointPosition + 1]
                    )
                    pointPosition++
                    delay(200)
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
        },
        draggable = false
    )
}


//            animation.animateTo(
//                targetValue = 1f,
//                animationSpec = tween(durationMillis = 5000, easing = LinearEasing),
//            ) {
//                if (pointPosition < directionPoint.size - 1) {
//                    val lat =
//                        animation.value * directionPoint[pointPosition + 1].latitude + (1 - animation.value) * directionPoint[pointPosition].latitude
//                    val lng =
//                        animation.value * directionPoint[pointPosition + 1].longitude + (1 - animation.value) * directionPoint[pointPosition].longitude
//                    markerPosition = LatLng(lat, lng)
//                    markerRotation = 180f + getCarRotation(
//                        directionPoint[pointPosition],
//                        directionPoint[pointPosition + 1]
//                    )
//                    pointPosition++
//                }
//            }
//            cameraPositionState.animate(
//                CameraUpdateFactory.newLatLngZoom(markerPosition, 13f),
//                300
//            )