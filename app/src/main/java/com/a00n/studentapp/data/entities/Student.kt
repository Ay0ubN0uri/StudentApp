package com.a00n.studentapp.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Student(
    val id: Int,
    val fname: String,
    val lname: String,
    val city: String,
    val gender: String
) : Parcelable