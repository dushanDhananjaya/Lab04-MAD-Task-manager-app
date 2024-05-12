package com.example.taskmanger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanger.db.TaskDB
import com.example.taskmanger.repository.TaskRepo
import com.example.taskmanger.viewModel.TaskViewModel
import com.example.taskmanger.viewModel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel(){
        val taskRepo = TaskRepo(TaskDB(this))
        val viewModelProviderFactory = TaskViewModelFactory(application, taskRepo)
        taskViewModel = ViewModelProvider(this, viewModelProviderFactory)[TaskViewModel::class.java]
    }
}