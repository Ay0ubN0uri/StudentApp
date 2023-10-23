package com.a00n.studentapp.ui.fragments.listfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a00n.studentapp.data.entities.Student
import com.a00n.studentapp.data.remote.MyRequestQueue
import com.a00n.studentapp.utils.Constants
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListViewModel(private val application: Application) : AndroidViewModel(application) {

    private val studentsList = MutableLiveData<List<Student>?>()


    fun getStudentsList(): MutableLiveData<List<Student>?> {
        return studentsList
    }

    fun deleteStudent(student: Student){
        val url: String = "${Constants.DELETE_STUDENT_URL}/${student.id}"
        val stringReq = StringRequest(
            Request.Method.DELETE, url,
            { response ->
                Log.i("info", "deleteStudent: ${response.toString()}")
            },
            {
                Log.i("info", "Error: ${it.message}")
            }
        )
        MyRequestQueue.getInstance(application.applicationContext).addToRequestQueue(stringReq)
    }

    fun fetchStudents() {
        val url: String = Constants.ALL_STUDENTS_URL

        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                val students: List<Student> = Gson().fromJson(
                    response.toString(),
                    object : TypeToken<List<Student>>() {}.type
                )
                studentsList.value = students
            },
            {
                studentsList.value = null
                Log.i("info", "getUsers: ${it.message}")
            }
        )
        MyRequestQueue.getInstance(application.applicationContext).addToRequestQueue(stringReq)
    }

}