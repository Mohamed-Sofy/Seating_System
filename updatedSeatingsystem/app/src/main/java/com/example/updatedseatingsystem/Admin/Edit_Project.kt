package com.example.updatedseatingsystem.Admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.adapters.ManagersAdapter
import com.example.updatedseatingsystem.adapters.ProjectsAdapter
import com.example.updatedseatingsystem.model.ManagersResponse
import com.example.updatedseatingsystem.model.ProjectsResponse
import com.example.updatedseatingsystem.service.APIClient

import kotlinx.android.synthetic.main.activity_edit__project.*
import kotlinx.android.synthetic.main.activity_edit_employee.*
import kotlinx.android.synthetic.main.content_edit__project.*
import kotlinx.android.synthetic.main.content_edit_employee.*
import kotlinx.android.synthetic.main.content_edit_managers.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Edit_Project : AppCompatActivity() {

    lateinit var mAdapter : ProjectsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit__project)
        shimmerLayout_project.startShimmerAnimation()
        add_project.setOnClickListener { view ->
           // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
           //     .setAction("Action", null).show()

            val `in` = Intent(applicationContext, Add_Project::class.java)
            startActivity(`in`)
        }
        get_Projects_Data()
    }

    override fun onResume() {
        super.onResume()

        get_Projects_Data()
    }

    fun get_Projects_Data(){
        managers_P_list.layoutManager = LinearLayoutManager(this)
        managers_P_list.setHasFixedSize(true)
        var client = ServiceGenerator.createService(APIClient::class.java)
        var call = client.retrieveAllProjects()
        call.enqueue(object : Callback, retrofit2.Callback<ProjectsResponse> {
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<ProjectsResponse>, t: Throwable) {
                add_project.visibility = View.GONE
                connection_project.visibility = View.VISIBLE
                shimmerLayout_project.startShimmerAnimation()
                shimmerLayout_project.visibility = View.GONE
            }

            @SuppressLint("RestrictedApi")
            override fun onResponse(call: Call<ProjectsResponse>, response: Response<ProjectsResponse>) {
                add_project.visibility = View.VISIBLE
                connection_project.visibility = View.GONE
                shimmerLayout_project.startShimmerAnimation()
                shimmerLayout_project.visibility = View.GONE
                if(response.isSuccessful){
                    shimmerLayout_project.startShimmerAnimation()
                    shimmerLayout_project.visibility = View.GONE
                    mAdapter= ProjectsAdapter(response.body()!!.result)
                    managers_P_list.adapter = mAdapter
                }
            }

        })
    }

}
