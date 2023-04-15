package com.volokhinaleksey.androidmaps.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.volokhinaleksey.androidmaps.R


@Composable
fun AnimationCarMovement(
    directionPoint: MutableList<LatLng>,
    cameraPositionState: CameraPositionState,
) {
    var isDriving by remember { mutableStateOf(false) }
    var carPosition by remember { mutableStateOf(directionPoint[0]) }
    var carRotation by remember {
        mutableStateOf(getCarRotation(directionPoint[0], directionPoint[1]))
    }
    if (isDriving) {
        AnimatedCar(
            directionPoint,
            carUpdateLocation = { newLatLng, rotation ->
                carPosition = newLatLng
                carRotation = rotation
            }
        )
    }
    Marker(
        state = MarkerState(carPosition),
        icon = bitmapDescriptorFromVector(LocalContext.current, R.drawable.car),
        rotation = carRotation,
        flat = true,
        onClick = {
            isDriving = !isDriving
            isDriving
        },
        anchor = Offset(0.5f, 0.5f)
    )
}

@Composable
fun AnimatedCar(directionPoint: MutableList<LatLng>, carUpdateLocation: (LatLng, Float) -> Unit) {
    val carAnimation = remember { Animatable(initialValue = 0f) }
    var carPosition by remember { mutableStateOf(directionPoint[0]) }
    var carRotation by remember {
        mutableStateOf(getCarRotation(begin = directionPoint[0], end = directionPoint[1]))
    }
    var pointPosition by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = true) {
        while (pointPosition != directionPoint.size - 1) {
            carAnimation.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            ) {
                val fraction = this.value
                val lat =
                    fraction * directionPoint[pointPosition + 1].latitude + (1 - fraction) * directionPoint[pointPosition].latitude
                val lng =
                    fraction * directionPoint[pointPosition + 1].longitude + (1 - fraction) * directionPoint[pointPosition].longitude
                val newLoc = LatLng(lat, lng)
                carPosition = newLoc
                carRotation = getCarRotation(directionPoint[pointPosition], newLoc)
                carUpdateLocation(newLoc, carRotation)
            }
            pointPosition++
            carAnimation.snapTo(0f)
        }
    }
}
