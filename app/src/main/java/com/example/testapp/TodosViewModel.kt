package com.example.testapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.dal.entities.Todo
import com.example.testapp.dal.entities.TodoAndOwner
import com.example.testapp.database.ApplicationDatabase
import com.example.testapp.repositories.TodoRepository

class TodosViewModel(application: Application) : ViewModel() {


    val allTodos: LiveData<List<TodoAndOwner>>
    private val repository: TodoRepository
    val searchResults: MutableLiveData<List<TodoAndOwner>>

    init {
        val todoDb = ApplicationDatabase.getInstance(application)
        val todoDao = todoDb.TodoDao()
        repository = TodoRepository(todoDao)

        allTodos = repository.allItems
        searchResults = repository.searchResults
    }

    fun insertTodo(todo:Todo)
    {
        repository.insert(todo)
    }

    fun findTodo(id:Int)
    {
        repository.find(id)
    }

    fun deleteTodo(todo:Todo)
    {
        repository.delete(todo)
    }

    fun deleteAll()
    {
        repository.deleteAll();
    }

    fun update(item:Todo)
    {
        repository.update(item)
    }


}