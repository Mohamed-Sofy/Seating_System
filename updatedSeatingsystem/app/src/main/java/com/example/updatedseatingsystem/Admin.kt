package com.example.updatedseatingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_admin.*

class Admin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        Edit_Manager_btn.setOnClickListener {
            val `in` = Intent(applicationContext, EditManagers::class.java)
           startActivity(`in`)
        }

        Edit_project_btn.setOnClickListener {
            val `in` = Intent(applicationContext, Edit_Project::class.java)
            startActivity(`in`)
        }

        Edit_view_employee_btn.setOnClickListener {
            val `in` = Intent(applicationContext, View_Employees::class.java)
            startActivity(`in`)
        }

        logout.setOnClickListener {
            val `in` = Intent(applicationContext, MainActivity::class.java)
            startActivity(`in`)
        }

    }
}
