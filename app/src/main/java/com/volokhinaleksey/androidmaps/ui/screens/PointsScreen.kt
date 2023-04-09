package com.volokhinaleksey.androidmaps.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.volokhinaleksey.androidmaps.models.Point
import com.volokhinaleksey.androidmaps.viewmodel.PointViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PointsScreen(pointViewModel: PointViewModel = koinViewModel()) {
    pointViewModel.getPoints().collectAsState(initial = emptyList()).value.let {
        LazyColumn {
            itemsIndexed(it) { _, item ->
                PointCard(point = item, onActionUpdatePoint = {
                    pointViewModel.update(point = it)
                }, onActionDeletePoint = {
                    pointViewModel.delete(point = it)
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointCard(
    point: Point,
    onActionUpdatePoint: (Point) -> Unit,
    onActionDeletePoint: (Point) -> Unit,
) {
    var title by rememberSaveable {
        mutableStateOf(point.title)
    }
    var description by rememberSaveable {
        mutableStateOf(point.description)
    }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                        onActionUpdatePoint(
                            Point(
                                id = point.id, lat = point.lat, lon = point.lon,
                                title = title, description = description
                            )
                        )
                    },
                    label = { Text(text = "Title") },
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                        onActionUpdatePoint(
                            Point(
                                id = point.id, lat = point.lat, lon = point.lon,
                                title = title, description = description
                            )
                        )
                    },
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Text(text = "Lat: ${point.lat}", fontSize = 18.sp)
                Text(text = "Lon: ${point.lon}", fontSize = 18.sp)
            }
            IconButton(
                onClick = { onActionDeletePoint(point) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = Icons.Default.Delete.name
                )
            }
        }
    }
}