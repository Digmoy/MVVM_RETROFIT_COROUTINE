package com.example.mvvm_corotine_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_corotine_kotlin.repository.AppRepository
import com.example.mvvm_corotine_kotlin.util.Resource
import com.example.mvvm_corotine_kotlin.viewmodel.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import com.hadi.retrofitmvvm.viewmodel.DemoViewModel

class MainActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var ed_phone : EditText
    lateinit var  btn_sign : Button

    lateinit var demoViewModel : DemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initView()
        btn_sign.setOnClickListener(this)
    }

    private fun initView() {
        btn_sign = findViewById(R.id.btn_sign)
        ed_phone = findViewById(R.id.ed_phone)
    }

    private fun init() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository)
        demoViewModel = ViewModelProvider(this, factory).get(DemoViewModel::class.java)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_sign ->
            {
                var phone = ed_phone.text.toString()

                if (phone.isNotEmpty()){
                    //  val body = RequestBodies.demoBody("MAYURANK@123",phone)
                    // val auth = "MAYURANK@123"
                    userPhoneCheck(phone)
                }
            }
        }
    }

    private fun userPhoneCheck(phone: String) {
        demoViewModel.demoUser(phone)

        demoViewModel.demoResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when(response)
                {
                    is Resource.Success ->{
                        response.data?.let { loginResponse ->
                            Toast.makeText(this,loginResponse.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is Resource.Error -> {
                        response.message?.let { message ->

                        }
                    }

                    is Resource.Loading -> {
                    }

                }
            }
        })
    }
}