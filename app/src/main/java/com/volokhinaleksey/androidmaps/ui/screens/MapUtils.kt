package com.volokhinaleksey.androidmaps.ui.screens

import com.google.android.gms.maps.model.LatLng
import kotlin.math.abs
import kotlin.math.atan

val road = listOf(
    LatLng(1.2816104951029417, 103.87605261057615),
    LatLng(1.2844180638825455, 103.87805018574),
    LatLng(1.2882241649126345, 103.87667588889599),
    LatLng(1.293153149472656, 103.8752419129014),
    LatLng(1.2995837755926738, 103.87755028903484),
    LatLng(1.3022666340075706, 103.87805018574),
    LatLng(1.3129977038624894, 103.8748674094677),
    LatLng(1.3197403384872208, 103.875552713871),
    LatLng(1.322423175302462, 103.88098418712616),
    LatLng(1.325043664534547, 103.8842923566699),
    LatLng(1.3301596103840008, 103.88510305434465),
    LatLng(1.3332791824605037, 103.88928562402725),
    LatLng(1.341706398207961, 103.88397954404354),
    LatLng(1.3488186614800077, 103.88035856187344),
)

fun getCarRotation(startLL: LatLng, endLL: LatLng): Float {
    val latDifference: Double = abs(startLL.latitude - endLL.latitude)
    val lngDifference: Double = abs(startLL.longitude - endLL.longitude)
    var rotation = -1F
    when {
        startLL.latitude < endLL.latitude && startLL.longitude < endLL.longitude -> {
            rotation = Math.toDegrees(atan(lngDifference / latDifference)).toFloat()
        }

        startLL.latitude >= endLL.latitude && startLL.longitude < endLL.longitude -> {
            rotation = (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 90).toFloat()
        }

        startLL.latitude >= endLL.latitude && startLL.longitude >= endLL.longitude -> {
            rotation = (Math.toDegrees(atan(lngDifference / latDifference)) + 180).toFloat()
        }

        startLL.latitude < endLL.latitude && startLL.longitude >= endLL.longitude -> {
            rotation =
                (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270).toFloat()
        }
    }
    return rotation
}