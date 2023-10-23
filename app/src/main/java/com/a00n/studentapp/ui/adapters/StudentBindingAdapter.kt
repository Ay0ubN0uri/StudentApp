package com.a00n.studentapp.ui.adapters

import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.a00n.studentapp.R
import com.a00n.studentapp.data.entities.Student
import com.a00n.studentapp.ui.fragments.listfragment.ListFragmentDirections
import com.google.android.material.card.MaterialCardView


@BindingAdapter("onItemClicked")
fun MaterialCardView.onItemClicked(student:Student) {
    this.setOnClickListener {
//        Log.i("info", "onItemClicked: $student")
        val action = ListFragmentDirections.actionListFragmentToUpdateFragment(student)
        this.findNavController().navigate(action)
    }
}