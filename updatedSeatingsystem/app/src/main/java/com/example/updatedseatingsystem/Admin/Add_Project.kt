package com.example.updatedseatingsystem.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.model.InsertManagerResponse
import com.example.updatedseatingsystem.model.JobsResponse
import com.example.updatedseatingsystem.model.ManagersResponse
import com.example.updatedseatingsystem.service.APIClient
import kotlinx.android.synthetic.main.activity_add__manager.*
import kotlinx.android.synthetic.main.activity_add__project.*
import kotlinx.android.synthetic.main.activity_fill_emp_details.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Add_Project : AppCompatActivity() {

    var managersNameArray = mutableListOf<String>()
    var managersIdArray = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__project)

        var client = ServiceGenerator.createService(APIClient::class.java)
        var call = client.retrieveAllManagers()
        call.enqueue(object : Callback, retrofit2.Callback<ManagersResponse> {
            override fun onFailure(call: Call<ManagersResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<ManagersResponse>, response: Response<ManagersResponse>) {
                if (response.isSuccessful) {

                    var managers = response.body()?.result
                    for (manager in managers!!) {
                        managersNameArray.add(manager.data_id.name)
                        managersIdArray.add(manager._id)
                    }
                    manager_spinner.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, managersNameArray)

                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }

        })

        add_project_button.setOnClickListener {
            sendNetworkRequest(
                e_name_add_project.text.toString())

        }

    }



    fun sendNetworkRequest(name : String){
        if(name.isEmpty()) {
            Toast.makeText(applicationContext, "Enter name of Project", Toast.LENGTH_LONG).show()
            return
        }
        var client = ServiceGenerator.createService(APIClient::class.java)
        var call = client.insertProject(name,managersIdArray[manager_spinner.selectedItemPosition])
        call.enqueue(object : Callback, retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Error occured, please try again",Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}
