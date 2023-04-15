package com.volokhinaleksey.androidmaps.ui.screens

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import kotlin.math.abs
import kotlin.math.atan

val road = mutableListOf(
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

fun getCarRotation(begin: LatLng, end: LatLng): Float {
    val lat: Double = abs(begin.latitude - end.latitude)
    val lng: Double = abs(begin.longitude - end.longitude)
    var rotation = -1F
    when {
        begin.latitude < end.latitude && begin.longitude < end.longitude -> {
            rotation = Math.toDegrees(atan(lng / lat)).toFloat()
        }

        begin.latitude >= end.latitude && begin.longitude < end.longitude -> {
            rotation = (90 - Math.toDegrees(atan(lng / lat)) + 90).toFloat()
        }

        begin.latitude >= end.latitude && begin.longitude >= end.longitude -> {
            rotation = (Math.toDegrees(atan(lng / lat)) + 180).toFloat()
        }

        begin.latitude < end.latitude && begin.longitude >= end.longitude -> {
            rotation =
                (90 - Math.toDegrees(atan(lng / lat)) + 270).toFloat()
        }
    }
    return rotation
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(android.graphics.Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}