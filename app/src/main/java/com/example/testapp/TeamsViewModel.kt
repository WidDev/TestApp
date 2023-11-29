package com.example.testapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.dal.entities.Team
import com.example.testapp.database.ApplicationDatabase
import com.example.testapp.repositories.TeamRepository

class TeamsViewModel(application:Application) : ViewModel() {

    val allItems: LiveData<List<Team>>
    private val repository: TeamRepository
    val searchResults: MutableLiveData<List<Team>>

    init {
        val db = ApplicationDatabase.getInstance(application)
        val dao = db.TeamDao()
        repository = TeamRepository(dao)

        allItems = repository.allItems
        searchResults = repository.searchResults
    }

    fun insertTodo(item: Team)
    {
        repository.insert(item)
    }

    fun findTodo(id:Int)
    {
        repository.find(id)
    }

    fun deleteTodo(item: Team)
    {
        repository.delete(item)
    }

    fun deleteAll()
    {
        repository.deleteAll();
    }


}