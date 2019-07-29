package com.example.updatedseatingsystem

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_edit_managers.*

class EditManagers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_managers)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
         //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
         //       .setAction("Action", null).show()

            val `in` = Intent(applicationContext, Add_Manager::class.java)
            startActivity(`in`)
        }
    }

}