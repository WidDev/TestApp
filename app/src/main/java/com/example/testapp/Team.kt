package com.example.testapp.models

import androidx.compose.ui.graphics.Color
import com.example.testapp.interfaces.IIdentifiable
import kotlinx.serialization.Serializable

@Serializable
data class Team(
    override public val id: Int,
    public var name:String,
    public var color: Color
) : IIdentifiable
