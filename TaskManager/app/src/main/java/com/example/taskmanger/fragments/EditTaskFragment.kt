package com.example.taskmanger.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskmanger.MainActivity
import com.example.taskmanger.R
import com.example.taskmanger.databinding.FragmentEditTaskBinding
import com.example.taskmanger.entity.Task
import com.example.taskmanger.viewModel.TaskViewModel


class EditTaskFragment : Fragment(R.layout.fragment_edit_task), MenuProvider {

    private var editTaskBinding: FragmentEditTaskBinding? = null
    private val binding get() = editTaskBinding!!

    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var current: Task

    private val args: EditTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editTaskBinding = FragmentEditTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel

        current = args.task!!

        binding.etEditTaskName.setText(current.taskName)
        binding.etEditTaskDescription.setText(current.taskDescription)

        binding.fbEditTask.setOnClickListener{
            val taskName = binding.etEditTaskName.text.toString().trim()
            val taskDescription = binding.etEditTaskDescription.text.toString().trim()

            if(taskName.isNotEmpty()){
                val task = Task(current.taskId, taskName, taskDescription)
                tasksViewModel.updateTask(task)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else{
                Toast.makeText(context, "Please enter task name", Toast.LENGTH_SHORT).show()
            }

        }
    }
    
    private fun deleteTask(){
        AlertDialog.Builder(activity).apply { 
            setTitle("Delete Task")
            setMessage("Do yoy want to delete this task")
            setPositiveButton("Delete"){_,_ ->
                tasksViewModel.deleteTask(current)
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_task, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteTask()
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editTaskBinding = null
    }


    
}