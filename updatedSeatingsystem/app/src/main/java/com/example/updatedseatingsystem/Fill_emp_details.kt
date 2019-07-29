package com.example.updatedseatingsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_fill_emp_details.*

class Fill_emp_details : AppCompatActivity() {


    var e_job =""
    var e_Project =""
    var e_Manager =""

    // QR code Scan
    var QR_code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_emp_details)


        val manager_Array = arrayOf("Manager", "Ahmed Hussien", "Mohamed Sofy", "Dania Alaaeldin ", "Ammar Sayed")
        val project_Array = arrayOf("Project", "hiemdal", "thor", "Seating System")
        val A_job = arrayOf("Job Title","Android", "IOS", "JAVA", "NodeJS", "Cloud", "Data Analytics")

        sp_project.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, project_Array)
        sp_manager.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, manager_Array)
        job.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, A_job)

        job.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        }

        sp_project.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    Toast.makeText(applicationContext, project_Array.get(position), Toast.LENGTH_SHORT).show()
                    e_Project = project_Array.get(position)
                } else {
                    Toast.makeText(applicationContext, "Please Select Item", Toast.LENGTH_SHORT).show()
                }

            }
        }

        sp_manager.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    Toast.makeText(applicationContext, manager_Array.get(position), Toast.LENGTH_SHORT).show()
                    e_Manager = manager_Array.get(position)
                } else {
                    Toast.makeText(applicationContext, "Please Select Item", Toast.LENGTH_SHORT).show()
                }
            }

        }

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


            /*   val str = "1/2/05"
               val arrOfStr = str.split("/".toRegex(), 3).toTypedArray()
               Floor = arrOfStr[0]
               Wing = arrOfStr[1]
               Seat = arrOfStr[2]
               */

            if(id.toString() != "") {
                if (name.equals("") || id.equals("") || phone.equals("") || job.equals("") ||
                    e_job.equals("Gender") || e_job.equals("") || e_Project.equals("Project") ||e_Project.equals("")
                    || e_Manager.equals("Manager") || e_Manager.equals("")) {
                    Toast.makeText(this, "Please, Fill Your Information ", Toast.LENGTH_LONG).show()
                } else {
                    if (QR_code.equals("")) {
                        Toast.makeText(this, "Please, Scan QR Code ", Toast.LENGTH_LONG).show()
                    } else {
                    /*    DB = DataBase(applicationContext)
                        var insert: Boolean = DB.insert_emp(
                            id, name, phone, e_gender, e_Manager,
                            e_Project, job, QR_code.toString(), "Employee"
                        )
                        if (insert == true) {
                            Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error in Insert", Toast.LENGTH_LONG).show()
                        }



                        val intent = Intent(this, Emp_details::class.java)
                        intent.putExtra("id", id)
                        ContextCompat.startActivity(this, intent, null)
                        */
                    }
                }
            }else{
                Toast.makeText(this, "Please, Enter Your ID", Toast.LENGTH_LONG).show()
            }

        }
    }
}
