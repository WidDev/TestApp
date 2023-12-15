package com.example.testapp.dal

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Upsert
import com.example.testapp.interfaces.IIdentifiable

interface BaseDao<T:IIdentifiable> {

    @Insert
    fun insert(entity: T)

    @Insert
    fun insert(entities: List<T>)

    @Upsert
    fun upsert(entity:T): Long

    @Update
    fun update(entity: T)

    @Update
    fun update(entities: List<T>)

    @Delete
    fun delete(entity: T)

    @Delete
    fun delete(entities: List<T>)

    fun deleteAll():Unit

    fun getAll() : LiveData<List<T>>

    fun find(id:Int) : List<T>


}