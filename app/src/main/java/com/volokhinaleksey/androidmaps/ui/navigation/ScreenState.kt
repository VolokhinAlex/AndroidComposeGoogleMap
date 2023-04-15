package com.volokhinaleksey.androidmaps.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.volokhinaleksey.androidmaps.R

/**
 * The class is needed for easy navigation between screens
 * @param route - Needed to determine the screen for navigation
 * @param title - Needed to display the screen name in the bottom navigation bar. The value as String Res
 * @param icon - Needed to display the screen icon in the bottom navigation bar
 */

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
