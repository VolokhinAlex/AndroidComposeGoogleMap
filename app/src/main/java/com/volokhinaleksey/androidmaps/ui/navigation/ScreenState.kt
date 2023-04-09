package com.volokhinaleksey.androidmaps.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.volokhinaleksey.androidmaps.R

sealed class ScreenState(val route: String, val icon: ImageVector, @StringRes val title: Int) {
    object MapScreen : ScreenState(
        route = "map_screen",
        icon = Icons.Default.LocationOn,
        title = R.string.map_screen
    )

    object PointsScreen : ScreenState(
        route = "points_screen",
        icon = Icons.Default.List,
        title = R.string.points_screen
    )
}
