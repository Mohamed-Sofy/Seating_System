package com.example.updatedseatingsystem.Admin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.model.*
import com.example.updatedseatingsystem.service.APIClient
import kotlinx.android.synthetic.main.activity_view__employees.*
import kotlinx.android.synthetic.main.show_emp_details.view.*
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder
import javax.security.auth.callback.Callback

class View_Employees : AppCompatActivity(), View.OnClickListener {

    lateinit var layout: ViewGroup

    var seats = ("____________/"
            + "UUUUU__UUUUU/"
            + "UUUUU__UUUUU/"
            + "____________/"
            + "UUUUU__UUUUU/"
            + "UUUUU__UUUUU/"
            + "____________/"
            + "UUUUU__UUUUU/"
            + "UUUUU__UUUUU/"
            + "____________/"
            + "UUUUU__UUUUU/"
            + "UUUUU__UUUUU/"
            + "____________/"
            + "UUUUU__UUUUU/"
            + "UUUUU__UUUUU/"
            + "____________/"
            + "UUUUU__UUUUU/"
            + "UUUUU__UUUUU/"
            + "____________/"
            + "UUUUU__UUUUU/"
            + "UUUUU__UUUUU/"
            + "____________/"
            + "____________/"
            + "____________/")

    var seatViewList: ArrayList<TextView> = ArrayList()
    val seatSize = 100
    val seatGaping = 10
    val statusAvailable = 1
    val statusBooked = 2
    lateinit var str : StringBuilder
    //////


    var managersNameArray = mutableListOf<String>()
    var managersIdArray = mutableListOf<String>()
    var projectsNameArray = mutableListOf<String>()
    var projectsIdArray = mutableListOf<String>()

     lateinit var resultProject : Array<SeatProjectResult>
     lateinit var resultManager : Array<SeatManagerResult>
     lateinit var manager : String
    /////////////

    var client = ServiceGenerator.createService(APIClient::class.java)
    lateinit var linLayout: LinearLayout

    var seatViews = mutableListOf<View>()

    override fun onClick(v: View?) {
        if (v!!.tag == statusAvailable) {
            if(sp_filter.selectedItemPosition == 0){
                //project
                var count = 0
                for(index in resultProject) {
                    if(index.seat_id.position == v.id){
                        str = StringBuilder(resultProject[count].seat_id.seatId)
                        str.insert(0,'F')
                        str.insert(3,'W')
                        str.insert(6,'S')
                        show_dialog(
                            resultProject[count].data_id.emp_id,
                            resultProject[count].data_id.name,
                            resultProject[count].data_id.phone,
                            resultProject[count].job_id.name,
                            manager,
                            resultProject[count].project_id.name,
                            str.toString()
                        )
                        return
                    }
                    count++
                }
            }else{
                //manager
                var count = 0
                for(index in resultManager) {

                    if(index.seat_id.position == v.id){
                        str = StringBuilder(resultManager[count].seat_id.seatId)
                        str.insert(0,'F')
                        str.insert(3,'W')
                        str.insert(6,'S')
                        show_dialog(
                            resultManager[count].data_id.emp_id,
                            resultManager[count].data_id.name,
                            resultManager[count].data_id.phone,
                            resultManager[count].job_id.name,
                            sp_manager_project.selectedItem.toString(),
                            resultManager[count].project_id.name,
                            str.toString()
                        )
                        return
                    }
                    count++
                }

            }



        } else if (v.tag == statusBooked) {
            Toast.makeText(this, "Seat " + v.id.toString() + " is empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view__employees)


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
                }
            }

        })


        var call2 = client.retrieveAllProjects()
        call2.enqueue(object : Callback, retrofit2.Callback<ProjectsResponse> {
            override fun onFailure(call: Call<ProjectsResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<ProjectsResponse>, response: Response<ProjectsResponse>) {
                if (response.isSuccessful) {
                    var projects = response.body()?.result
                    for (project in projects!!) {
                        projectsNameArray.add(project.name)
                        projectsIdArray.add(project._id)
                    }
                    sp_manager_project.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, projectsNameArray)


                }
            }

        })

        layout = findViewById(R.id.layoutSeat)
        seats = "/" + seats

        val layoutSeat = LinearLayout(this)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutSeat.setOrientation(LinearLayout.VERTICAL)
        layoutSeat.setLayoutParams(params)
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping)
        layout.addView(layoutSeat)

        var count = 0


        for (index in 0 until seats.length) {
            if (seats[index] == '/') {
                linLayout = LinearLayout(this)
                linLayout.setOrientation(LinearLayout.HORIZONTAL)
                layoutSeat.addView(linLayout)
            } else if (seats[index] == 'U') {

                var view = TextView(this)
                var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.setLayoutParams(layoutParams)
                view.setPadding(0, 0, 0, 2 * seatGaping)
                view.setId(count)
                view.setGravity(Gravity.CENTER)
                view.setBackgroundResource(R.drawable.unavaliable)
                // view.setTextColor(Color.WHITE)
                view.setTag(statusBooked)
                // view.setText(count + "")
                // view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9)
                linLayout.addView(view)
                seatViewList.add(view)
                seatViews.add(view)
                view.setOnClickListener(this)
                count++
            } else if (seats[index] == '_') {
                var view: TextView = TextView(this)
                var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.setLayoutParams(layoutParams)
                view.setBackgroundColor(Color.TRANSPARENT)
                linLayout.addView(view)
            }
        }

        ///////
        //spiner

        val filter_Array = arrayOf("Projects", "Managers")
        sp_filter.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filter_Array)

        sp_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    sp_manager_project.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, projectsNameArray)
                } else {
                    sp_manager_project.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, managersNameArray)

                }
            }

        }

        sp_manager_project.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                for (index in 0 until seatViews.count()) {
                    var view = seatViews[index]
                    view.setBackgroundResource(R.drawable.unavaliable)
                    view.setTag(statusBooked)
                }

                if (sp_filter.selectedItemPosition == 0) {
                    // Project
                    var call = client.retrieveSeatsByProjectId(projectsIdArray[position])
                    call.enqueue(object : Callback, retrofit2.Callback<SeatProjectResponse> {
                        override fun onFailure(call: Call<SeatProjectResponse>, t: Throwable) {

                        }

                        override fun onResponse(
                            call: Call<SeatProjectResponse>,
                            response: Response<SeatProjectResponse>
                        ) {

                            if (response.isSuccessful) {
                                 resultProject = response.body()!!.result
                                 manager = response.body()!!.manager
                                /////////////////////////////////////////////////////////////


                                for (seat in resultProject) {
                                    var view = seatViews[seat.seat_id.position]
                                    view.setBackgroundResource(R.drawable.green)
                                    view.setTag(statusAvailable)
                                }

                                ////////////////////////////////////////////////////////////

                            }
                        }

                    })

                } else {
                    //Manager
                    var call = client.retrieveSeatsByManagerId(managersIdArray[position])
                    call.enqueue(object : Callback, retrofit2.Callback<SeatManagerResponse> {
                        override fun onFailure(call: Call<SeatManagerResponse>, t: Throwable) {

                        }

                        override fun onResponse(
                            call: Call<SeatManagerResponse>,
                            response: Response<SeatManagerResponse>
                        ) {

                            if (response.isSuccessful) {
                                 resultManager = response.body()!!.result
                                /////////////////////////////////////////////////////////////

                                for (seat in resultManager) {
                                    var view = seatViews[seat.seat_id.position]
                                    view.setBackgroundResource(R.drawable.green)
                                    view.setTag(statusAvailable)
                                }

                                ////////////////////////////////////////////////////////////

                            }
                        }

                    })
                }
            }

        }




    }
    fun show_dialog(
        id: String,
        name: String,
        phone: String,
        job: String,
        manager: String,
        project: String,
        seat: String
    ) {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.show_emp_details, null)
        val mBuilder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()
        mDialogView.idText.text = id
        mDialogView.nameText.text = name
        mDialogView.phoneText.text = phone
        mDialogView.jobText.text = job
        mDialogView.managerText.text = manager
        mDialogView.projectText.text = project
        mDialogView.seatText.text = seat

        mDialogView.finish.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
    }
}
