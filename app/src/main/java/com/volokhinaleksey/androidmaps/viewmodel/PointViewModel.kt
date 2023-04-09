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

    /**
     * Method for adding a marker to the repository
     * @param point - The marker to add
     */

    fun addPoint(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(point)
        }
    }

    /**
     * Method for getting a list of markers from the repository
     */

    fun getPoints() = repository.getPoints()

    /**
     * Method for deleting a marker from the repository
     * @param point - The marker to delete
     */

    fun delete(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(point)
        }
    }

    /**
     * Method for getting an address by lat lon coordinates
     * @param geocoder - Object for getting an address by coordinates
     * @param lat - The latitude coordinate for getting the address
     * @param lon - The longitude coordinate for getting the address
     */

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

    /**
     * Method for updating a marker in the repository
     * @param point - The marker to update
     */

    fun update(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(point)
        }
    }
}
