package com.example.testapp.dal.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.testapp.dal.BaseDao
import com.example.testapp.dal.entities.Todo
import com.example.testapp.dal.entities.TodoAndOwner


@Dao
interface TodoDao : BaseDao<Todo> {

    @Query("SELECT * FROM todos WHERE id = :id")
    override fun find(id: Int): List<Todo>

    @Query("SELECT * FROM todos WHERE id = :id")
    fun findWithOwner(id: Int): List<TodoAndOwner>

    @Query("SELECT * FROM todos order by [order] asc")
    override fun getAll() : LiveData<List<Todo>>

    @Query("DELETE from todos")
    override fun deleteAll() : Unit

    @Query("SELECT * FROM todos order by [order] asc")
    fun getAllWithOwner() : LiveData<List<TodoAndOwner>>

    @Query("select id from todos order by id desc limit 1")
    fun getHighestId(): Int

    @Query("update todos set [order] = [order]+1 where [order] >= :from")
    fun moveAllUpFrom(from:Int)

    @Query("update todos set [order] = [order] - 1 where [order] > :to")
    fun moveAllDownFrom(to:Int)

    @Query("update todos set [order] = :newOrder where id = :id")
    fun updateOrder(id:Int, newOrder:Int)
    @Query("select id from Todos where id = :id")
    fun itemExists(id:Int) : Boolean

    @Query("select id from Todos where [order] = :order")
    fun getIdByOrder(order:Int):Int

/*    @Query()
    fun swapOrder(from:Int, to:Int)*/

}