package com.example.updatedseatingsystem

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.Admin.Admin
import com.example.updatedseatingsystem.Manager.Manager
import com.example.updatedseatingsystem.model.LoginResponse
import com.example.updatedseatingsystem.service.APIClient
import com.example.updatedseatingsystem.storage.SharedPrefManager
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      var  dialog = SpotsDialog.Builder().setContext(this).build()


        login_btn.setOnClickListener {
            var user_name = userName.text.toString()
            var pass = password.text.toString()
            if(user_name.isEmpty() || pass.isEmpty()){
                Toast.makeText(applicationContext,"Enter user name and password",Toast.LENGTH_LONG).show()
            }

           else if(user_name.equals("Admin") && pass.equals("123") ){
                SharedPrefManager.getInstance(applicationContext).saveAdminId(5)
                var intent = Intent(applicationContext, Admin::class.java).apply {
                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)

            }else{
                dialog.show()
                var client = ServiceGenerator.createService(APIClient::class.java)
                var call = client.login(user_name,pass)
                call.enqueue(object :  retrofit2.Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        dialog.dismiss()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        dialog.dismiss()
                        if(response.isSuccessful){
                            var manager_id = response.body()?.manager_id
                            var manager_name = response.body()?.manager_name
                            SharedPrefManager.getInstance(applicationContext).saveManagerId(manager_id)
                            SharedPrefManager.getInstance(applicationContext).saveManagerName(manager_name)
                            var intent = Intent(applicationContext, Manager::class.java).apply {
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            startActivity(intent)

                        }else{
                            Toast.makeText(applicationContext,"Invalid credentials",Toast.LENGTH_LONG).show()
                        }
                    }

                })

            }

        }


    }

}
