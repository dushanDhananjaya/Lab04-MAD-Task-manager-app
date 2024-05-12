package com.example.taskmanger.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.taskmanger.MainActivity
import com.example.taskmanger.R
import com.example.taskmanger.adapter.TaskAdapter
import com.example.taskmanger.databinding.FragmentHomeBinding
import com.example.taskmanger.entity.Task
import com.example.taskmanger.viewModel.TaskViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {
    
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel
        setUpHomeRecyclerView()

        binding.fbAddTask.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }
    }

    private fun updateUI(task: List<Task>?){
        if(task != null){
            if(task.isNotEmpty()){
                binding.emptyNotesImage.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            } else{
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }
    }

    private fun setUpHomeRecyclerView(){
        taskAdapter = TaskAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)

            adapter = taskAdapter
        }

        activity?.let{
            tasksViewModel.getAllTasks().observe(viewLifecycleOwner){task ->
                taskAdapter.differ.submitList(task)

                updateUI(task)
            }
        }
    }
    
    private fun searchTask(query: String?){
        val querySearch = "%$query"
        
        tasksViewModel.search(querySearch).observe(this) {list ->
            taskAdapter.differ.submitList(list)
            
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchTask(newText)
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home, menu)

        val search = menu.findItem(R.id.search).actionView as SearchView
        
        search.isSubmitButtonEnabled = false
        search.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

}