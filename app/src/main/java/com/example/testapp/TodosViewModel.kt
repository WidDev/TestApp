package com.example.testapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.models.ToDo

abstract class TodosViewModel(application:Application) : ViewModel(){



    abstract val allItems: LiveData<List<ToDo>>

}