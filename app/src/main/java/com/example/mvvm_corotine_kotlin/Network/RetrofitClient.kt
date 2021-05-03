package com.example.mvvm_corotine_kotlin.Network


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val URL = "https://projects.venturesupport.in/mayurank/api/"

    private val retrofitClient: Retrofit.Builder by lazy {

        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val headerInterceptor = Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder().addHeader("X-API-KEY","MAYURANK@123").build()

            val response = chain.proceed(request)

            response
        }

        val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(logger)


//        val levelType: HttpLoggingInterceptor.Level
//        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
//            levelType = HttpLoggingInterceptor.Level.BODY else levelType = HttpLoggingInterceptor.Level.NONE
//
//        val logging = HttpLoggingInterceptor()
//        logging.setLevel(levelType)
//
//        val okhttpClient = OkHttpClient.Builder()
//        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttp.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: API by lazy {
        retrofitClient
            .build()
            .create(API::class.java)
    }
}