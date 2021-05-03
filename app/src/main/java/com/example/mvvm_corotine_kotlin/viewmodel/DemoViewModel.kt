package com.hadi.retrofitmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm_corotine_kotlin.R
import com.example.mvvm_corotine_kotlin.UserLoginModel.UserLoginModel
import com.example.mvvm_corotine_kotlin.app.MyApplication
import com.example.mvvm_corotine_kotlin.repository.AppRepository
import com.example.mvvm_corotine_kotlin.util.Event
import com.example.mvvm_corotine_kotlin.util.Resource
import com.example.mvvm_corotine_kotlin.util.Utils

import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DemoViewModel (app : Application, private val appRepository : AppRepository) : AndroidViewModel(app) {

    private val _demoResponse = MutableLiveData<Event<Resource<UserLoginModel>>>()
    val demoResponse : LiveData<Event<Resource<UserLoginModel>>> = _demoResponse

    fun demoUser (phone : String?) = viewModelScope.launch {
        demoLogin(phone)
    }

    private suspend fun demoLogin(phone: String?) {
        _demoResponse.postValue(Event(Resource.Loading()))

        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.demoLogin(phone)
                _demoResponse.postValue(handleResponse(response))
            } else {
                _demoResponse.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(
                    R.string.no_internet_connection))))
            }
        }
        catch (t : Throwable)
        {
            when (t) {
                is IOException -> {
                    _demoResponse.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _demoResponse.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.conversion_error
                            )
                        ))
                    )
                }
            }
        }
    }

    private fun handleResponse(response: Response<UserLoginModel>): Event<Resource<UserLoginModel>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

}