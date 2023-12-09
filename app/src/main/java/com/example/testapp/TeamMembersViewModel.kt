package com.example.testapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.dal.entities.TeamMember
import com.example.testapp.database.ApplicationDatabase
import com.example.testapp.repositories.TeamMemberRepository

class TeamMembersViewModel(application: Application) : ViewModel() {

    val allItems: LiveData<List<TeamMember>>
    private val repository: TeamMemberRepository
    val searchResults: MutableLiveData<List<TeamMember>>

    init {
        val db = ApplicationDatabase.getInstance(application)
        val dao = db.TeamMemberDao()
        repository = TeamMemberRepository(dao)

        allItems = repository.allItems
        searchResults = repository.searchResults
    }

    fun insert(item: TeamMember)
    {
        repository.insert(item)
    }

    fun upsert(item:TeamMember)
    {
        repository.upsert(item)
    }

    fun find(id:Int)
    {
        repository.find(id)
    }

    fun delete(item: TeamMember)
    {
        repository.delete(item)
    }

    fun deleteAll()
    {
        repository.deleteAll();
    }


}