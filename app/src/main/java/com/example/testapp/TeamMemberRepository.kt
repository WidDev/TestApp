package com.example.testapp.repositories

import com.example.testapp.dal.dao.TeamMemberDao
import com.example.testapp.dal.entities.TeamMember
import com.example.testapp.repositories.BaseRepository

class TeamMemberRepository(private val dao: TeamMemberDao) : BaseRepository<TeamMember>(dao) {
}