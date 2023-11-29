package com.example.testapp.dal.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.testapp.dal.BaseDao
import com.example.testapp.dal.entities.TeamMember


@Dao()
interface TeamMemberDao : BaseDao<TeamMember> {

    @Query("SELECT * FROM teammembers WHERE id = :id")
    override fun find(id: Int): List<TeamMember>

    @Query("SELECT * FROM teammembers order by id asc")
    override fun getAll() : LiveData<List<TeamMember>>

    @Query("DELETE from teammembers")
    override fun deleteAll() : Unit

}