package com.example.testapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.database.TodoRoomDatabase
import com.example.testapp.models.Todo
import com.example.testapp.repositories.TodoRepository

class TodosViewModel(application: Application) : ViewModel() {


    val allTodos: LiveData<List<Todo>>
    private val repository: TodoRepository
    val searchResults: MutableLiveData<List<Todo>>

    init {
        val todoDb = TodoRoomDatabase.getInstance(application)
        val todoDao = todoDb.TodoDao()
        repository = TodoRepository(todoDao)

        allTodos = repository.allTodos
        searchResults = repository.searchResults
    }

    fun insertTodo(todo:Todo)
    {
        repository.insertTodo(todo)
    }

    fun findTodo(id:Int)
    {
        repository.findTodo(id)
    }

    fun deleteTodo(todo:Todo)
    {
        repository.deleteTodo(todo)
    }

    fun deleteAll()
    {
        repository.deleteAll();
    }


}