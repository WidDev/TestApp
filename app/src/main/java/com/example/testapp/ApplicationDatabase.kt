package com.example.testapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapp.dal.dao.TeamDao
import com.example.testapp.dal.dao.TeamMemberDao
import com.example.testapp.dal.dao.TodoDao
import com.example.testapp.dal.entities.Team
import com.example.testapp.dal.entities.TeamMember
import com.example.testapp.dal.entities.Todo


//@TypeConverters(ColorConverter::class)
@Database(entities = [(Todo::class), (Team::class), (TeamMember::class)], version = 8)
abstract class ApplicationDatabase : RoomDatabase(){


    abstract fun TodoDao() : TodoDao
    abstract fun TeamDao() : TeamDao
    abstract fun TeamMemberDao(): TeamMemberDao

    companion object {

        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase
        {
            synchronized(this)
            {
                var instance = INSTANCE

                if(instance == null)
                {
                    instance = Room.databaseBuilder(context.applicationContext,
                            ApplicationDatabase::class.java,
                        "todo_database").fallbackToDestructiveMigration().build()
                }

                INSTANCE = instance
                return instance
            }

        }

    }

}