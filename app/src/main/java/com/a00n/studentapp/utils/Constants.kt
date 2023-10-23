package com.a00n.studentapp.utils

class Constants {
    companion object {
        private const val URL: String = "http://192.168.43.106:8080"
        const val ALL_STUDENTS_URL: String = "$URL/students/all"
        const val DELETE_STUDENT_URL: String = "$URL/students/student/delete"
        const val ADD_STUDENT_URL: String = "$URL/students/create"
        const val UPDATE_STUDENT_URL: String = "$URL/students/student"
    }
}