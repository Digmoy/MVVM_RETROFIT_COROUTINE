package com.example.mvvm_corotine_kotlin.repository


import com.example.mvvm_corotine_kotlin.Network.RetrofitClient



class AppRepository {
    suspend fun demoLogin ( phone : String?) = RetrofitClient.apiInterface.userLogin(phone)
}