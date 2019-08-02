package com.example.updatedseatingsystem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.model.Manager
import com.example.updatedseatingsystem.model.Project

class ManagersAdapter (list : Array<Manager>) : RecyclerView.Adapter<ManagersAdapter.ManagerViewHolder>() {

   private var managersList = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.manager_post_layout, parent, false)
        return ManagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return managersList.count()
    }

    override fun onBindViewHolder(holder: ManagerViewHolder, position: Int) {
        holder.managerName.text = managersList[position].data_id.name
        holder.managerJob.text = managersList[position].job_id.name
        holder.managerid.text = managersList[position].data_id.emp_id
    }


    class ManagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var managerName : TextView = itemView.findViewById(R.id.M_name_managers)
        var managerid : TextView = itemView.findViewById(R.id.M_id_managers)
        var managerJob : TextView = itemView.findViewById(R.id.M_job_managers)
    }
}