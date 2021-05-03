package com.example.mvvm_corotine_kotlin.Network

object RequestBodies {

    data class LoginBody(
        val email:String,
        val password:String
    )

}