package com.example.updatedseatingsystem.Manager

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.model.InsertEmpResponse
import com.example.updatedseatingsystem.model.JobsResponse
import com.example.updatedseatingsystem.model.ProjectsResponse
import com.example.updatedseatingsystem.service.APIClient
import com.example.updatedseatingsystem.storage.SharedPrefManager
import com.google.zxing.integration.android.IntentIntegrator
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_add__manager.*
import kotlinx.android.synthetic.main.activity_fill_emp_details.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder
import javax.security.auth.callback.Callback

class Fill_emp_details : AppCompatActivity() {


    var e_job = ""
    var e_Project = ""
    var e_Manager = ""

    // QR code Scan
    var QR_number = ""
    var QR_code = ""
    var jobsArray = mutableListOf<String>()
    var jobsIdArray = mutableListOf<String>()
    var projectsArray = mutableListOf<String>()
    var projectsIdArray = mutableListOf<String>()
    lateinit var dialog: AlertDialog
    lateinit var str : StringBuilder



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_emp_details)
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
                        jobsArray.add(job.name)
                        jobsIdArray.add(job._id)
                    }
                    job.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, jobsArray)

                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
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
                        projectsArray.add(project.name)
                        projectsIdArray.add(project._id)
                    }
                    sp_project.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, projectsArray)


                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }

        })


        /* job.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
             override fun onNothingSelected(parent: AdapterView<*>?) {

             }

             override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                 if (position > 0) {
                     Toast.makeText(applicationContext, project_Array.get(position), Toast.LENGTH_SHORT).show()
                     e_job = project_Array.get(position)
                 } else {
                     Toast.makeText(applicationContext, "Please Select Item", Toast.LENGTH_SHORT).show()
                 }

             }

         }*/




        scan_btn.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }

        continue_btn.setOnClickListener {


            var name = e_name.text.toString()
            var id = e_id.text.toString()
            var phone = e_phone.text.toString()
            var project = projectsIdArray[sp_project.selectedItemPosition]
            var job = jobsIdArray[job.selectedItemPosition]
            var manager = SharedPrefManager.getInstance(this).managerId

/*   val str = "1/2/05"
val arrOfStr = str.split("/".toRegex(), 3).toTypedArray()
Floor = arrOfStr[0]
Wing = arrOfStr[1]
Seat = arrOfStr[2]
*/


            if (name.equals("") || id.equals("") || phone.equals("")) {
                Toast.makeText(this, "Fill Your Information ", Toast.LENGTH_LONG).show()
            } else {
                if (QR_code.equals("")) {
                    Toast.makeText(this, "Please, Scan QR Code ", Toast.LENGTH_LONG).show()
                } else {
                    dialog.show()
                    var client = ServiceGenerator.createService(APIClient::class.java)
                    var call = client.insertEmployee(
                        name,
                        phone,
                        id,
                        job,
                        manager,
                        project,
                        QR_code,
                        QR_number
                    )
                    call.enqueue(object : Callback, retrofit2.Callback<InsertEmpResponse> {
                        override fun onFailure(call: Call<InsertEmpResponse>, t: Throwable) {
                            dialog.dismiss()
                        }

                        override fun onResponse(call: Call<InsertEmpResponse>, response: Response<InsertEmpResponse>) {
                            dialog.dismiss()
                            if (response.isSuccessful) {
                                finish()
                            } else {
                                Toast.makeText(applicationContext, "ID or QR code already exist", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                    })

                }
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "" + result.contents, Toast.LENGTH_LONG).show()
                    QR_code = result.contents.toString()
                    QR_number = QR_code.takeLast(2)


                    str = StringBuilder(QR_code)
                    str.insert(0,'F')
                    str.insert(3,'W')
                    str.insert(6,'S')
                    scan_btn.text = "Seat Location: " + str.toString()

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
