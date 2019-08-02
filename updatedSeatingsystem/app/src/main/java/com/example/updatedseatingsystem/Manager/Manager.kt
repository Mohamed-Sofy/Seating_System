package com.example.updatedseatingsystem.Manager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.updatedseatingsystem.Admin.EditManagers
import com.example.updatedseatingsystem.MainActivity
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_manager.*

class Manager : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)
        var managerName = SharedPrefManager.getInstance(applicationContext).managerName
        welcome_tv.append(""+"\n"+managerName)
        Edit_employee_btn.setOnClickListener {
            val `in` = Intent(applicationContext, Edit_employee::class.java)
            startActivity(`in`)

        }

        view_btn.setOnClickListener {
            val `in` = Intent(applicationContext, Manager_View::class.java)
            startActivity(`in`)
        }

        logout.setOnClickListener {
            SharedPrefManager.getInstance(applicationContext).clear()
            var intent = Intent(applicationContext, MainActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }
    }
}
