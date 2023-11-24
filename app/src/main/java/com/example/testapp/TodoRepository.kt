package com.example.testapp.repositories

import com.example.testapp.dal.dao.TodoDao
import com.example.testapp.models.Todo

class TodoRepository(private val dao: TodoDao) : BaseRepository<Todo>(dao){

}