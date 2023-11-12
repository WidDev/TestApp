package com.example.testapp.services

import androidx.compose.ui.graphics.Color
import com.example.testapp.models.Team
import java.util.UUID


class TeamService private constructor() {

    companion object {

        @Volatile
        private var instance: TeamService? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: TeamService().also { instance = it }
            }
    }


    private var teams:MutableList<Team> = mutableListOf<Team>()

    public fun AddTeam(name:String, color: Color = Color.Red)
    {
        teams.add(Team(id = UUID.randomUUID(), name = name, color = color))
    }




}