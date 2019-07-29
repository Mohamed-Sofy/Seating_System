package com.example.updatedseatingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add__manager.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var user_name = userName.text
        var pass = password.text

        login_btn.setOnClickListener {

            if(user_name.toString().equals("Manager") && pass.toString().equals("123") ){
                val `in` = Intent(applicationContext, Edit_employee::class.java)
                startActivity(`in`)
            }
           // val `in` = Intent(applicationContext, Admin::class.java)
           // startActivity(`in`)
        }


    }
}
