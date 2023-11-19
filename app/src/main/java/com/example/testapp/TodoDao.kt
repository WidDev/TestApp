package com.example.testapp.dal.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.models.ToDo

@Dao
interface TodoDao {

    @Insert
    fun insertTodo(todo: ToDo)

    @Query("SELECT * FROM todos WHERE id = :id")
    fun findTodo(id: Int): List<ToDo>

    @Query("DELETE FROM todos WHERE id = :id")
    fun deleteTodo(id:Int)

    @Query("SELECT * FROM todos")
    fun getAllTodos() : LiveData<List<ToDo>>

}