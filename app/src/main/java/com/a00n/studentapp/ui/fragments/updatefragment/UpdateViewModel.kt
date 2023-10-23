package com.a00n.studentapp.ui.fragments.updatefragment

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

class UpdateViewModel(private val application: Application) : AndroidViewModel(application) {
    val studentUpdated = MutableLiveData<Boolean>()
    private val gson = Gson()

    fun updateStudent(student: Student){
        val url: String = "${Constants.UPDATE_STUDENT_URL}/${student.id}"
        val jsonStudent = gson.toJson(student)
        val request = JsonObjectRequest(
            Request.Method.PUT, url, JSONObject(jsonStudent),
            { response ->
                studentUpdated.value = true
                Log.i("info", "deleteStudent: ${response.toString()}")
            },
            {
                studentUpdated.value = false
                Log.i("info", "Error: ${it.message}")
            }
        )
        MyRequestQueue.getInstance(application.applicationContext).addToRequestQueue(request)
    }
}