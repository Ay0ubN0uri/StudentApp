package com.a00n.studentapp.ui.fragments.listfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a00n.studentapp.R
import com.a00n.studentapp.data.entities.Student
import com.a00n.studentapp.databinding.FragmentListBinding
import com.a00n.studentapp.ui.adapters.StudentsListAdapter
import com.a00n.studentapp.utils.SwipeGesture
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: StudentsListAdapter by lazy { StudentsListAdapter() }

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        binding.studentRecycleView.adapter = adapter
        toggleViews(false)
        binding.emptyListLinearLayout.visibility = View.GONE
        showShimmer()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        viewModel.getStudentsList().observe(viewLifecycleOwner) { students ->
            Handler(Looper.getMainLooper()).postDelayed({
                hideShimmer()
                if (students != null) {
                    Log.i("info", "onCreateView: $students")
                    if(students.isEmpty()){
                        toggleViews(true)
                    }
                    else{
                        toggleViews(false)
                    }
                    adapter.submitList(students)
                } else {
                    adapter.submitList(null)
                    toggleViews(true)
                    Log.i("info", "onCreateView: Error has occured")
                }
            },1000)
        }
        viewModel.fetchStudents()
        addSwipeDelete()
        return binding.root
    }

    private fun addSwipeDelete() {
        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.i(
                    "info",
                    "onSwiped: ${(viewHolder as StudentsListAdapter.StudentViewHolder).itemView}"
                )
                val originalList = adapter.currentList
                val list = originalList.toMutableList()
                list.removeAt(viewHolder.adapterPosition)
                adapter.submitList(list)
                val snackbar = Snackbar.make(
                    binding.root,
                    "Student deleted successfully.",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction("Undo") {
                    adapter.submitList(originalList)
//                    toggleViews(false)
                }
                snackbar.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        Log.i("info", "onDismissed: $event")
                        if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_CONSECUTIVE) {
                            val diff = (originalList.toSet() subtract list.toSet()).toList()
                            if (diff.isNotEmpty()) {
                                viewModel.deleteStudent(diff[0])
                                Log.i("info", "deleting: ${diff[0]}")
                            }
                        }
                    }
                })
                if (list.isEmpty()) {
                    toggleViews(true)
                }
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(binding.studentRecycleView)
    }

    private fun toggleViews(visible: Boolean) {
        binding.emptyListLinearLayout.visibility = if (visible) View.VISIBLE else View.GONE
    }


    private fun showShimmer() = binding.studentRecycleView.showShimmer()
    private fun hideShimmer() = binding.studentRecycleView.hideShimmer()


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}