package com.example.updatedseatingsystem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.model.Project

class ProjectsAdapter(list : Array<Project>) : RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {

     var projectsList = list



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.project_post_layout,parent,false)
        return ProjectsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return projectsList.count()
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        holder.projectName.text = projectsList[position].name
        holder.projectManager.text = projectsList[position].manager_name
    }


    class ProjectsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var projectName : TextView = itemView.findViewById(R.id.P_name_projects)
        var projectManager : TextView = itemView.findViewById(R.id.manager_projects)
    }
}