package com.example.testapp.dal.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.testapp.dal.BaseDao
import com.example.testapp.dal.entities.Team

@Dao()
interface TeamDao : BaseDao<Team> {

    @Query("SELECT * FROM teams WHERE id = :id")
    override fun find(id: Int): List<Team>

    @Query("SELECT * FROM teams order by id asc")
    override fun getAll() : LiveData<List<Team>>

    @Query("DELETE from teams")
    override fun deleteAll() : Unit

}