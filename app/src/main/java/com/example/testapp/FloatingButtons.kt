package com.example.testapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
public fun FloatingButton(onClick:() -> Unit)
{
    Box(modifier = Modifier.fillMaxSize().padding(10.dp))
    {
        FloatingActionButton(
            onClick = onClick,
            shape = ShapeDefaults.Small,
            modifier = Modifier.align(alignment = Alignment.BottomEnd),
            containerColor = Color.LightGray,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation()
        ) {
            Text("ADD")
        }
    }
}
