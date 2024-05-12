package com.example.taskmanger.repository

import com.example.taskmanger.db.TaskDB
import com.example.taskmanger.entity.Task

class TaskRepo(private val db: TaskDB) {

    suspend fun insertTasK(task: Task) = db.getTaskDAO().insertTask(task)
    suspend fun updateTasK(task: Task) = db.getTaskDAO().updateTask(task)
    suspend fun deleteTasK(task: Task) = db.getTaskDAO().deleteTask(task)

    fun getAllTasks() = db.getTaskDAO().getAllTasks()
    fun search(query: String?) = db.getTaskDAO().search(query)
}