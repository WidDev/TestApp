package com.example.testapp.dal.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.models.Todo

@Dao
interface TodoDao {

    @Insert
    fun insertTodo(todo: Todo)

    @Query("SELECT * FROM todos WHERE id = :id")
    fun findTodo(id: Int): List<Todo>

    @Query("DELETE FROM todos WHERE id = :id")
    fun deleteTodo(id:Int)

    @Query("SELECT * FROM todos")
    fun getAllTodos() : LiveData<List<Todo>>

}