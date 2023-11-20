package com.example.testapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapp.interfaces.IIdentifiable

@Entity(tableName="todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    override var id: Int,
    val txt:String,
    /*val owner:TeamMember? = null*/
) : IIdentifiable
