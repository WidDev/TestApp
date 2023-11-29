package com.example.testapp.repositories

import com.example.testapp.dal.dao.TeamDao
import com.example.testapp.dal.entities.Team
import com.example.testapp.repositories.BaseRepository

class TeamRepository(private val dao: TeamDao) : BaseRepository<Team>(dao) {
}