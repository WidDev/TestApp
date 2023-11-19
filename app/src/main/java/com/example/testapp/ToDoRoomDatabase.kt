package com.example.testapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapp.dal.dao.TodoDao
import com.example.testapp.models.ToDo

@Database(entities = [(ToDo::class)], version = 1)
abstract class TodoRoomDatabase : RoomDatabase(){


    abstract fun TodoDao() : TodoDao

    companion object {

        private var INSTANCE: TodoRoomDatabase? = null

        fun getInstance(context: Context): TodoRoomDatabase
        {
            synchronized(this)
            {
                var instance = INSTANCE

                if(instance == null)
                {
                    instance = Room.databaseBuilder(context.applicationContext,
                            TodoRoomDatabase::class.java,
                        "todo_database").fallbackToDestructiveMigration().build()
                }

                INSTANCE = instance
                return instance
            }

        }

    }

}