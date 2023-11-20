package com.example.testapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.dal.dao.TodoDao
import com.example.testapp.models.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<Todo>> = todoDao.getAllTodos()
    val searchResults = MutableLiveData<List<Todo>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertTodo(todo:Todo)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            todoDao.insertTodo(todo)
        }
    }

    fun deleteTodo(id:Int)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            todoDao.deleteTodo(id)
        }
    }

    fun findTodo(id:Int)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            searchResults.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: Int): Deferred<List<Todo>?> =
        coroutineScope.async(Dispatchers.IO)
        {
            return@async todoDao.findTodo(id)
        }




}