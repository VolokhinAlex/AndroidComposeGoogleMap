package com.volokhinaleksey.androidmaps.viewmodel

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.androidmaps.models.Point
import com.volokhinaleksey.androidmaps.repository.PointsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PointViewModel(
    private val repository: PointsRepository
) : ViewModel() {

    fun addPoint(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(point)
        }
    }

    fun getPoints() = repository.getPoints()

    fun delete(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(point)
        }
    }

    fun getAddressFromLocation(geocoder: Geocoder, lat: Double, lon: Double): Address? {
        var addresses: List<Address>? = null
        try {
            addresses =
                geocoder.getFromLocation(lat, lon, 1)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return addresses?.get(0)
    }

    fun update(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(point)
        }
    }
}
