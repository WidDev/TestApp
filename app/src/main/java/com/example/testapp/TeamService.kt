package com.example.testapp.services


import android.graphics.Color
import com.example.testapp.dal.entities.Team
import kotlin.random.Random


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

    public fun AddTeam(name:String, color: Int = Color.DKGRAY)
    {
        teams.add(Team(id = Random.nextInt(), name = name, color = color))
    }




}