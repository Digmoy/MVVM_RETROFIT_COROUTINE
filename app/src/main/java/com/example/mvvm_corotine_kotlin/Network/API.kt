package com.example.mvvm_corotine_kotlin.Network


import com.example.mvvm_corotine_kotlin.UserLoginModel.UserLoginModel
import retrofit2.Response
import retrofit2.http.*

interface API {

    @FormUrlEncoded
    @POST("user/phone_send_otp")
    suspend fun userLogin (@Field("phone") phone : String?) : Response<UserLoginModel>
}