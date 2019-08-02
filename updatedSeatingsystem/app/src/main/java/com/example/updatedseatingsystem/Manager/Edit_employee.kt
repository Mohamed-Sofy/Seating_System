package com.example.updatedseatingsystem.Manager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.adapters.EmployeeAdapter
import com.example.updatedseatingsystem.adapters.ManagersAdapter
import com.example.updatedseatingsystem.model.EmployeesResponse
import com.example.updatedseatingsystem.model.ManagersResponse
import com.example.updatedseatingsystem.service.APIClient
import com.example.updatedseatingsystem.storage.SharedPrefManager

import kotlinx.android.synthetic.main.activity_edit_employee.*
import kotlinx.android.synthetic.main.content_edit_employee.*
import kotlinx.android.synthetic.main.content_edit_managers.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Edit_employee : AppCompatActivity() {

    lateinit var mAdapter : EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_employee)
        shimmerLayout_employee.startShimmerAnimation()
        employees_list.layoutManager = LinearLayoutManager(this)
        employees_list.setHasFixedSize(true)
        add_emp.setOnClickListener { view ->
           // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
           //     .setAction("Action", null).show()


            val `in` = Intent(applicationContext, Fill_emp_details::class.java)
            startActivity(`in`)
        }
    }

    override fun onResume() {
        super.onResume()
        get_employee_data()
    }


     fun get_employee_data(){
         var manager_id = SharedPrefManager.getInstance(this).managerId
         var client = ServiceGenerator.createService(APIClient::class.java)
         var call = client.retrieveAllEmployees(manager_id)
         call.enqueue(object : retrofit2.Callback<EmployeesResponse> {
             @SuppressLint("RestrictedApi")
             override fun onFailure(call: Call<EmployeesResponse>, t: Throwable) {
                 add_emp.visibility = View.GONE
                 connection_employee.visibility = View.VISIBLE
                 shimmerLayout_employee.startShimmerAnimation()
                 shimmerLayout_employee.visibility = View.GONE


             }

             @SuppressLint("RestrictedApi")
             override fun onResponse(call: Call<EmployeesResponse>, response: Response<EmployeesResponse>) {
                 add_emp.visibility = View.VISIBLE
                 connection_employee.visibility = View.GONE
                 shimmerLayout_employee.startShimmerAnimation()
                 shimmerLayout_employee.visibility = View.GONE
                 if(response.isSuccessful){
                     mAdapter= EmployeeAdapter(response.body()!!.result)
                     employees_list.adapter = mAdapter
                 }
             }
         })
     }


}
