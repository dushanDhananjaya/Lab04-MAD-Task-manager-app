package com.example.taskmanger.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanger.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDB: RoomDatabase() {

    abstract fun getTaskDAO(): TaskDAO

    companion object{
        @Volatile
        private var instance: TaskDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =

            Room.databaseBuilder(
                context.applicationContext,
                TaskDB::class.java,
                "task_db"
            ).build()

    }

}