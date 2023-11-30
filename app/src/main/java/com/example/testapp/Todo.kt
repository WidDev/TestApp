package com.example.testapp.dal.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.example.testapp.interfaces.IIdentifiable

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
    val txt:String,
    val owner:Int? = null
) : IIdentifiable



/*data class GfgCourseNameWithPlaylists(
    @Embedded val gfgCourseName: GfgCourseName,
    @Relation(
        parentColumn = "gfgCourseNameId",
        entityColumn = "gfgCourseNameCreatorId"
    )
    val playlists: List<Playlist>
)*/
