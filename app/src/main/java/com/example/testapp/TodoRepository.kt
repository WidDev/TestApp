package com.example.testapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.dal.dao.TodoDao
import com.example.testapp.dal.entities.Todo
import com.example.testapp.dal.entities.TodoAndOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TodoRepository(private val dao: TodoDao) {

    val allItems: LiveData<List<TodoAndOwner>> = dao.getAllWithOwner()
    val searchResults = MutableLiveData<List<TodoAndOwner>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insert(item: Todo) {
        coroutineScope.launch(Dispatchers.IO)
        {
            dao.moveAllUpFrom(1)
            item.order = 1
            dao.insert(item)
        }
    }

    fun delete(item: Todo) {
        coroutineScope.launch(Dispatchers.IO)
        {
            dao.delete(item)
            dao.moveAllDownFrom(item.order)
        }
    }

    fun update(item: Todo) {
        coroutineScope.launch(Dispatchers.IO)
        {
            dao.update(item)
        }
    }

    fun upsert(item: Todo) {
        coroutineScope.launch(Dispatchers.IO)
        {

            var existing = dao.itemExists(item.id)
            if(!existing)
            {
                dao.moveAllUpFrom(0)
                item.order = 0
                dao.insert(item)

            }
            else
            {
                dao.update(item)
            }


        }
    }

    fun find(id: Int) {
        coroutineScope.launch(Dispatchers.IO)
        {
            searchResults.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: Int): Deferred<List<TodoAndOwner>?> =
        coroutineScope.async(Dispatchers.IO)
        {
            return@async dao.findWithOwner(id)
        }

    fun deleteAll() {
        coroutineScope.launch(Dispatchers.IO)
        {
            dao.deleteAll()
        }
    }

    fun updateOrder(from:Int, to:Int)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            val id = dao.getIdByOrder(from)
            dao.moveAllDownFrom(from)
            dao.moveAllUpFrom(to)
            dao.updateOrder(id, to)
        }
    }

}