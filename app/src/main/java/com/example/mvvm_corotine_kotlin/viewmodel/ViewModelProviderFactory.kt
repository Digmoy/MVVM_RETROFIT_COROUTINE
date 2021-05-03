package com.example.mvvm_corotine_kotlin.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_corotine_kotlin.repository.AppRepository
import com.hadi.retrofitmvvm.viewmodel.DemoViewModel

class ViewModelProviderFactory(
    val app: Application,
    val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DemoViewModel::class.java))
        {
            return DemoViewModel(app,appRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}