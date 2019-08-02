package com.example.updatedseatingsystem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.model.Employee
import com.example.updatedseatingsystem.model.Manager

class EmployeeAdapter (list : Array<Employee>) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    private var employeesList = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter.EmployeeViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.employee_post_layout, parent, false)
        return EmployeeAdapter.EmployeeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return employeesList.count()    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {

        holder.employeetName.text = employeesList[position].data_id.name
        holder.employeeJob.text = employeesList[position].job_id.name
        holder.employeeID.text = employeesList[position].data_id.emp_id
    }

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var employeetName : TextView = itemView.findViewById(R.id.E_name)
        var employeeID : TextView = itemView.findViewById(R.id.E_id)
        var employeeJob : TextView = itemView.findViewById(R.id.E_job)

    }
}