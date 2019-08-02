package com.example.updatedseatingsystem.Admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.adapters.ManagersAdapter
import com.example.updatedseatingsystem.model.Manager
import com.example.updatedseatingsystem.model.ManagersResponse
import com.example.updatedseatingsystem.service.APIClient
import kotlinx.android.synthetic.main.activity_edit_employee.*

import kotlinx.android.synthetic.main.activity_edit_managers.*
import kotlinx.android.synthetic.main.content_edit__project.*
import kotlinx.android.synthetic.main.content_edit_employee.*
import kotlinx.android.synthetic.main.content_edit_managers.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class EditManagers : AppCompatActivity() {

    lateinit var mAdapter : ManagersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_managers)
        shimmerLayout_manager.startShimmerAnimation()
        managers_list.layoutManager = LinearLayoutManager(this)
        managers_list.setHasFixedSize(true)

        add_manager.setOnClickListener {
         //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
         //       .setAction("Action", null).show()

            val `in` = Intent(applicationContext, Add_Manager::class.java)
            startActivity(`in`)
        }

        get_manager_data()

    }



    override fun onResume() {
        super.onResume()
        get_manager_data()
    }


    fun get_manager_data(){

        var client = ServiceGenerator.createService(APIClient::class.java)
        var call = client.retrieveAllManagers()
        call.enqueue(object : Callback, retrofit2.Callback<ManagersResponse> {
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<ManagersResponse>, t: Throwable) {
                add_manager.visibility = View.GONE
                connection_manager.visibility = View.VISIBLE
                shimmerLayout_manager.startShimmerAnimation()
                shimmerLayout_manager.visibility = View.GONE
            }

            @SuppressLint("RestrictedApi")
            override fun onResponse(call: Call<ManagersResponse>, response: Response<ManagersResponse>) {
                add_manager.visibility = View.VISIBLE
                connection_manager.visibility = View.GONE
                if(response.isSuccessful){
                    shimmerLayout_manager.startShimmerAnimation()
                    shimmerLayout_manager.visibility = View.GONE
                    mAdapter= ManagersAdapter(response.body()!!.result)
                    managers_list.adapter = mAdapter
                }
            }
        })
    }
}
