package com.a00n.studentapp.ui.fragments.addfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a00n.studentapp.data.entities.Student
import com.a00n.studentapp.data.remote.MyRequestQueue
import com.a00n.studentapp.utils.Constants
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import org.json.JSONObject

class AddViewModel(private val application: Application) : AndroidViewModel(application) {

    val studentAdded = MutableLiveData<Boolean>()
    val gson = Gson()

    fun addStudent(student: Student){
        val url: String = Constants.ADD_STUDENT_URL
        val jsonStudent = gson.toJson(student)
        val request = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(jsonStudent),
            { response ->
                studentAdded.value = true
                Log.i("info", "deleteStudent: ${response.toString()}")
            },
            {
                studentAdded.value = false
                Log.i("info", "Error: ${it.message}")
            }
        )
        MyRequestQueue.getInstance(application.applicationContext).addToRequestQueue(request)
    }
}