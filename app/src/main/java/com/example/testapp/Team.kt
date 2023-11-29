package com.example.testapp.dal.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapp.interfaces.IIdentifiable

@Entity(tableName="teams")
data class Team(
    @PrimaryKey(autoGenerate = true)
    override public val id: Int,
    public var name:String,
    public var color: Int
) : IIdentifiable


