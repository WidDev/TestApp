package com.example.testapp.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


data class NavigationDrawerItem(
    public var route:String = "none",
    public var label:String = "Undefined",
    public var color:Color = Color.Red,
    public var icon:ImageVector = Icons.Filled.Settings
)