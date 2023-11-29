package com.example.testapp.utils.converters

import androidx.room.TypeConverter
import com.example.testapp.dal.entities.Team


class TeamConverter {

    @TypeConverter
    fun fromTeam(team: Team) : Int {
        return team.id
    }
    /*@TypeConverter
    fun toTeam(id:Int) : Team {
        return Team(id)
    }*/

}