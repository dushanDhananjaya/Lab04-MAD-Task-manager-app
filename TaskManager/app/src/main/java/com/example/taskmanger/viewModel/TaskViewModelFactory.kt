package com.example.taskmanger.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanger.repository.TaskRepo

class TaskViewModelFactory(val app: Application, private val taskRepo: TaskRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(app, taskRepo) as T
    }
}