package com.example.testapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import com.example.testapp.dal.BaseDao
import com.example.testapp.interfaces.IIdentifiable
import com.example.testapp.repositories.BaseRepository

class BaseViewModel<T:IIdentifiable, D>(val roomDatabase:RoomDatabase, val dao: BaseDao<T>, val repository: BaseRepository<T>) : ViewModel() {


    val allTodos: LiveData<List<T>>
    val searchResults: MutableLiveData<List<T>>


    init {
        allTodos = repository.allTodos
        searchResults = repository.searchResults
    }

    fun insert(item:T)
    {
        repository.insert(item)
    }

    fun find(id:Int)
    {
        repository.find(id)
    }

    fun delete(item:T)
    {
        repository.delete(item)
    }

}