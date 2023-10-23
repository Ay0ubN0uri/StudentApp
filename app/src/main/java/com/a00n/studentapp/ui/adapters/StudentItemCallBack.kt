package com.a00n.studentapp.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.a00n.studentapp.data.entities.Student

class StudentItemCallBack : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem == newItem
    }
}