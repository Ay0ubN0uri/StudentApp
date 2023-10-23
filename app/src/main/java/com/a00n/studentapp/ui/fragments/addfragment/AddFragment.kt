package com.a00n.studentapp.ui.fragments.addfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.a00n.studentapp.R
import com.a00n.studentapp.data.entities.Student
import com.a00n.studentapp.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar

class AddFragment : Fragment() {

    private lateinit var viewModel: AddViewModel
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[AddViewModel::class.java]
        val genders = resources.getStringArray(R.array.genders)
        binding.spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,genders)
        viewModel.studentAdded.observe(viewLifecycleOwner){
            it.let {added ->
                var msg = "Error adding a new student"
                if(added){
                    msg = "Student Added Successfully"
                }
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
                    .show()
                findNavController().navigateUp()
            }
        }
        binding.addStudentBtn.setOnClickListener {
            val fname = binding.fNameEditText.text.toString()
            val lname = binding.lNameEditText.text.toString()
            val city = binding.cityEditText.text.toString()
            if(fname.isEmpty()){
                Snackbar.make(binding.root, "Please fill the first name!!!", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            if(lname.isEmpty()){
                Snackbar.make(binding.root, "Please fill the last name!!!", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            if(city.isEmpty()){
                Snackbar.make(binding.root, "Please fill the city!!!", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            Log.i("info", "onCreateView: ${binding.spinner.selectedItem}")
            val student = Student(0,fname,lname,city,binding.spinner.selectedItem!! as String)
            viewModel.addStudent(student)
        }
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}