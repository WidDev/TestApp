package com.example.testapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.dal.BaseDao
import com.example.testapp.interfaces.IIdentifiable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BaseRepository<T:IIdentifiable>(private val dao: BaseDao<T>) {

    val allTodos: LiveData<List<T>> = dao.getAll()
    val searchResults = MutableLiveData<List<T>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insert(item:T)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            dao.insert(item)
        }
    }

    fun delete(item:T)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            dao.delete(item)
        }
    }

    fun find(id:Int)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            searchResults.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: Int): Deferred<List<T>?> =
        coroutineScope.async(Dispatchers.IO)
        {
            return@async dao.find(id)
        }




}