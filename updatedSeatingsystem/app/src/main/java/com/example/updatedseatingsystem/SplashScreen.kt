package com.example.updatedseatingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.updatedseatingsystem.Admin.Admin
import com.example.updatedseatingsystem.Manager.Manager
import com.example.updatedseatingsystem.storage.SharedPrefManager

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mythread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    if(SharedPrefManager.getInstance(applicationContext).isLoggedInManager){
                        var intent = Intent(applicationContext, Manager::class.java)
                        startActivity(intent)
                        finish()
                    }else if (SharedPrefManager.getInstance(applicationContext).isLoggedInAdmin){
                        var intent = Intent(applicationContext, Admin::class.java)
                        startActivity(intent)
                        finish()
                    } else{
                        var intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
        mythread.start()

    }
}
