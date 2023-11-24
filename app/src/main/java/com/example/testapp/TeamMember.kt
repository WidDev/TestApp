package com.example.testapp.models

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapp.interfaces.IIdentifiable

@Entity(tableName="teammembers")
data class TeamMember(
    @PrimaryKey(autoGenerate = true)
    override public val id: Int,
    public var name:String,
    public var team:Team?,
    public var color: Color = Color.Gray
) : IIdentifiable