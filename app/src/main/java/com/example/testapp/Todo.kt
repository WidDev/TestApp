package com.example.testapp.dal.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.testapp.interfaces.IIdentifiable
import com.example.testapp.interfaces.IOrderable

/*@Entity(tableName="todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    val txt:String,
    *//*val owner:TeamMember? = null*//*
) : IIdentifiable*/

@Entity(tableName="todos",
    foreignKeys = [ForeignKey(entity = TeamMember::class,
                            parentColumns = ["id"],
                            childColumns = ["owner"],
                            onDelete = CASCADE)])
data class Todo(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    var txt:String,
    var owner:Int? = null,
    override var order:Int = 0
) : IIdentifiable, IOrderable

data class TodoAndOwner(
    @Embedded
    val todo:Todo,
    @Relation(
        parentColumn = "owner",
        entityColumn = "id"
    )
    val owner:TeamMember?
)

data class TodoDTO(val txt:String, val color:Int, val owner:Int?)
