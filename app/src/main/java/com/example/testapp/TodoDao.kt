package com.example.testapp.dal.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.testapp.dal.BaseDao
import com.example.testapp.dal.entities.Todo
import com.example.testapp.dal.entities.TodoAndOwner


@Dao
interface TodoDao : BaseDao<Todo> {

    @Query("SELECT * FROM todos WHERE id = :id")
    override fun find(id: Int): List<Todo>

    @Query("SELECT * FROM todos WHERE id = :id")
    fun findWithOwner(id: Int): List<TodoAndOwner>

    @Query("SELECT * FROM todos order by id asc")
    override fun getAll() : LiveData<List<Todo>>

    @Query("DELETE from todos")
    override fun deleteAll() : Unit

    @Query("SELECT * FROM todos order by id asc")
    fun getAllWithOwner() : LiveData<List<TodoAndOwner>>

    @Upsert
    fun upsert(item:Todo)

}