package com.example.testapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.testapp.models.NavigationDrawerItem

class MainActivityViewModel : ViewModel() {

    public var navigationItems:List<NavigationDrawerItem> = listOf(
        NavigationDrawerItem("todo", "Actions", Color.Green, Icons.Filled.CheckCircle),
        NavigationDrawerItem("teams", "Teams", Color.Blue, Icons.Filled.AccountCircle),
        NavigationDrawerItem("people", "Team Members", Color.Magenta, Icons.Filled.Face)
    )
}