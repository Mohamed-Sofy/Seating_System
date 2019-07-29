package com.example.updatedseatingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val mythread = object : Thread() {
            override fun run() {
                try {
                    sleep(4000)
                    val `in` = Intent(applicationContext, MainActivity::class.java)
                    startActivity(`in`)
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
        mythread.start()
    }
}
