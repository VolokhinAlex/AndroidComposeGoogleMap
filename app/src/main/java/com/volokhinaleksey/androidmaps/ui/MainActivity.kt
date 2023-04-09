package com.volokhinaleksey.androidmaps.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.volokhinaleksey.androidmaps.ui.navigation.ScreenState
import com.volokhinaleksey.androidmaps.ui.screens.MapScreen
import com.volokhinaleksey.androidmaps.ui.screens.PointsScreen

class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val locationPermissionsState = rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            )
            if (locationPermissionsState.allPermissionsGranted) {
                Navigation()
            } else {
                Column {
                    val allPermissionsRevoked =
                        locationPermissionsState.permissions.size ==
                                locationPermissionsState.revokedPermissions.size
                    if (!allPermissionsRevoked) {
                        Navigation()
                    } else {
                        LaunchedEffect(key1 = true) {
                            locationPermissionsState.launchMultiplePermissionRequest()
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    BottomNavigationBar(navController) {
        NavHost(
            navController = navController,
            startDestination = ScreenState.MapScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(route = ScreenState.MapScreen.route) {
                MapScreen()
            }
            composable(route = ScreenState.PointsScreen.route) {
                PointsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val bottomNavItems =
        listOf(ScreenState.MapScreen, ScreenState.PointsScreen)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    val selected = item.route == currentRoute
                    NavigationBarItem(
                        selected = selected,
                        onClick = { navController.navigate(item.route) },
                        label = {
                            Text(
                                text = stringResource(id = item.title),
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.icon.name,
                            )
                        }
                    )
                }
            }
        },
        content = content
    )
}
