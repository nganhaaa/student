package com.example.bai1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bai1.R
import com.example.bai1.StudentAdapter
import com.example.bai1.StudentViewModel
import com.example.bai1.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {
    private var bindingContainer: FragmentStudentListBinding? = null
    private val binding get() = bindingContainer!!
    
    private val vm: StudentViewModel by activityViewModels()
    private lateinit var mainAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingContainer = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupEventObservers()
    }

    private fun setupListView() {
        mainAdapter = StudentAdapter(mutableListOf()) { item ->
            vm.setDetailStudent(item)
            findNavController().navigate(R.id.action_studentListFragment_to_studentDetailFragment)
        }
        
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = mainAdapter
        
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
        }
    }

    private fun setupEventObservers() {
        vm.students.observe(viewLifecycleOwner) { data ->
            mainAdapter.refreshData(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingContainer = null
    }
}
