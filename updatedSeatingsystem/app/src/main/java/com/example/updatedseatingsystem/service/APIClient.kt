package com.example.updatedseatingsystem.service

import android.util.Log
import com.example.updatedseatingsystem.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface APIClient {

    @FormUrlEncoded
    @POST("insertEmployee")
     fun insertEmployee(
        @Field("name") name: String,
        @Field("phone") phone : String,
        @Field("emp_id") emp_id : String,
        @Field("job_id") job_id: String,
        @Field("manager_id") manager_id : String,
        @Field("project_id") project_id: String,
        @Field("seatId") seat_id :String,
        @Field("position") position: String
    ): Call<InsertEmpResponse>



    @GET("retrieveAllJobs")
    fun retrieveAllJobs() : Call<JobsResponse>

    @GET("retrieveEmployeeByManagerId")
    fun retrieveAllEmployees(
        @Query("manager_id") manager_id: String
    ) : Call<EmployeesResponse>

    @GET("retrieveAllProjects")
    fun retrieveAllProjects() : Call<ProjectsResponse>


    @GET("retrieveAllManagers")
    fun retrieveAllManagers() : Call<ManagersResponse>

    @FormUrlEncoded
    @POST("insertManager")
    fun insertManager(
        @Field("name") name : String,
        @Field("phone") phone :String,
        @Field("emp_id") emp_id : String,
        @Field("username") user : String,
        @Field("password") pass  : String,
        @Field("job_id") job_id : String) : Call<InsertManagerResponse>

    @FormUrlEncoded
    @POST("insertProject")
    fun insertProject(
        @Field("name") name : String,
        @Field("manager_id") manager_id : String) : Call<ResponseBody>


    @GET("retrieveSeatsByProjectId")
    fun retrieveSeatsByProjectId(
        @Query("project_id") project_id : String
    ) : Call<SeatProjectResponse>


    @GET("retrieveSeatsByManagerId")
    fun retrieveSeatsByManagerId(
        @Query("manager_id") manager_id : String
    ) : Call<SeatManagerResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username : String,
        @Field("password") password : String) : Call<LoginResponse>
}