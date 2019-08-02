package com.example.updatedseatingsystem.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.model.InsertManagerResponse
import com.example.updatedseatingsystem.model.JobsResponse
import com.example.updatedseatingsystem.service.APIClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_add__manager.*
import kotlinx.android.synthetic.main.activity_fill_emp_details.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Add_Manager : AppCompatActivity() {


    var jobsNameArray = mutableListOf<String>()
    var jobsIdArray = mutableListOf<String>()
  lateinit var dialog : android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__manager)
         dialog = SpotsDialog.Builder().setContext(this).build()


        var client = ServiceGenerator.createService(APIClient::class.java)
        var call = client.retrieveAllJobs()
        call.enqueue(object : Callback, retrofit2.Callback<JobsResponse> {
            override fun onFailure(call: Call<JobsResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<JobsResponse>, response: Response<JobsResponse>) {
                if (response.isSuccessful) {

                    var jobs = response.body()?.result
                    for (job in jobs!!) {
                        jobsNameArray.add(job.name)
                        jobsIdArray.add(job._id)
                    }
                    jobs_spinner.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, jobsNameArray)

                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }

        })


        add_manager_button.setOnClickListener {
            sendNetworkRequest(
                e_name_add_manager.text.toString(),
                e_id_add_manager.text.toString(),
                e_phone_add_manager.text.toString(),
                user_name_add_manager.text.toString(),
                pass_add_manager.text.toString()
            )
        }

    }

    fun sendNetworkRequest(name: String, id: String, phone: String, user: String, password: String) {
        if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || user.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Fill information of Manager", Toast.LENGTH_LONG).show()
            return
        }
        dialog.show()
        var client = ServiceGenerator.createService(APIClient::class.java)
        var call = client.insertManager(name, phone, id, user, password, jobsIdArray[jobs_spinner.selectedItemPosition])
        call.enqueue(object : Callback, retrofit2.Callback<InsertManagerResponse> {
            override fun onFailure(call: Call<InsertManagerResponse>, t: Throwable) {
                dialog.dismiss()
            }

            override fun onResponse(call: Call<InsertManagerResponse>, response: Response<InsertManagerResponse>) {
                dialog.dismiss()
                if (response.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(applicationContext, "ID or username already exist", Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}
