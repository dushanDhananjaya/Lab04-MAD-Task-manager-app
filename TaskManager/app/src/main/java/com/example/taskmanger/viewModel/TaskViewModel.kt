package com.example.taskmanger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanger.entity.Task
import com.example.taskmanger.repository.TaskRepo
import kotlinx.coroutines.launch

class TaskViewModel(app: Application, private val taskRepo: TaskRepo): AndroidViewModel(app) {

    fun insertTask(task: Task) =
        viewModelScope.launch {
            taskRepo.insertTasK(task)
        }

    fun updateTask(task: Task) =
        viewModelScope.launch {
            taskRepo.updateTasK(task)
        }

    fun deleteTask(task: Task) =
        viewModelScope.launch {
            taskRepo.deleteTasK(task)
        }

    fun getAllTasks() = taskRepo.getAllTasks()

    fun search(query: String?) = taskRepo.search(query)
}