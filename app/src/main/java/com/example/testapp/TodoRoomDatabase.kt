package com.example.testapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapp.dal.dao.TeamDao
import com.example.testapp.dal.dao.TodoDao
import com.example.testapp.models.Team
import com.example.testapp.models.Todo

//@TypeConverters(ColorConverter::class)
@Database(entities = [(Todo::class), (Team::class)], version = 2)
abstract class TodoRoomDatabase : RoomDatabase(){


    abstract fun TodoDao() : TodoDao
    abstract fun TeamDao() : TeamDao

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