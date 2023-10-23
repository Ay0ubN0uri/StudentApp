package com.a00n.studentapp.ui.fragments.updatefragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.a00n.studentapp.R
import com.a00n.studentapp.data.entities.Student
import com.a00n.studentapp.databinding.FragmentUpdateBinding
import com.google.android.material.snackbar.Snackbar

class UpdateFragment : Fragment() {


    private lateinit var viewModel: UpdateViewModel
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[UpdateViewModel::class.java]
        val genders = resources.getStringArray(R.array.genders)
        binding.updateSpinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,genders)
        binding.updateSpinner.setSelection(if(args.student.gender=="MALE") 0 else 1)
        binding.student = args.student
        viewModel.studentUpdated.observe(viewLifecycleOwner){
            it.let {updated ->
                var msg = "Error updating a new student"
                if(updated){
                    msg = "Student Updated Successfully"
                }
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
                    .show()
                findNavController().navigateUp()
            }
        }
        binding.updateStudentBtn.setOnClickListener {
            val fname = binding.updateFNameEditText.text.toString()
            val lname = binding.updateLNameEditText.text.toString()
            val city = binding.updateCityEditText.text.toString()
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
            Log.i("info", "onCreateView: ${binding.updateSpinner.selectedItem}")
            val student = Student(args.student.id,fname,lname,city,binding.updateSpinner.selectedItem!! as String)
            viewModel.updateStudent(student)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}