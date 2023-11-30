package com.example.testapp.dal.entities

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapp.interfaces.IIdentifiable

@Entity(tableName="teammembers")
data class TeamMember(
    @PrimaryKey(autoGenerate = true)
    override public val id: Int = 0,
    public var name:String,
    public var color: Int = Color.DKGRAY
) : IIdentifiable