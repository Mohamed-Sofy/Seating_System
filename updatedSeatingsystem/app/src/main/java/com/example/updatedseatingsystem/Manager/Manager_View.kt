package com.example.updatedseatingsystem.Manager

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.seatingsystem.service.ServiceGenerator
import com.example.updatedseatingsystem.R
import com.example.updatedseatingsystem.model.SeatManagerResponse
import com.example.updatedseatingsystem.model.SeatManagerResult
import com.example.updatedseatingsystem.service.APIClient
import com.example.updatedseatingsystem.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_view__employees.*
import kotlinx.android.synthetic.main.show_emp_details.view.*
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder
import javax.security.auth.callback.Callback

class Manager_View : AppCompatActivity() , View.OnClickListener{


    lateinit var layout: ViewGroup
    lateinit var resultManager : Array<SeatManagerResult>

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
            )

    var seatViewList: ArrayList<TextView> = ArrayList()
    val seatSize = 100
    val seatGaping = 10
    val statusAvailable = 1
    val statusBooked = 2

    var seatViews = mutableListOf<View>()
    lateinit var str : StringBuilder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager__view)

        layout = findViewById(R.id.layoutSeat)
        seats = "/" + seats

        val layoutSeat = LinearLayout(this)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutSeat.setOrientation(LinearLayout.VERTICAL)
        layoutSeat.setLayoutParams(params)
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping)
        layout.addView(layoutSeat)

        var count = 0
        var linLayout: LinearLayout? = null

        for (index in 0..seats.length-1) {
            if (seats[index].toChar() == '/') {
                linLayout = LinearLayout(this)
                linLayout.setOrientation(LinearLayout.HORIZONTAL)
                layoutSeat.addView(linLayout)
            } else if (seats[index] == 'U') {
                var view: TextView = TextView(this)
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
                linLayout!!.addView(view)
                seatViewList.add(view)
                view.setOnClickListener(this)
                seatViews.add(view)
                count++
            }  else if (seats[index] == '_') {
                var view: TextView = TextView(this)
                var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.setLayoutParams(layoutParams)
                view.setBackgroundColor(Color.TRANSPARENT)
                linLayout!!.addView(view)
            }
        }

        var manager_id = SharedPrefManager.getInstance(this).managerId
        var client = ServiceGenerator.createService(APIClient::class.java)
        var call = client.retrieveSeatsByManagerId(manager_id)
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

    override fun onClick(v: View?) {
        if (v!!.tag == statusAvailable) {
            var count = 0
            for(index in resultManager) {

                if(index.seat_id.position == v.id){
                    var managerName = SharedPrefManager.getInstance(applicationContext).managerName
                    str = StringBuilder(resultManager[count].seat_id.seatId)
                    str.insert(0,'F')
                    str.insert(3,'W')
                    str.insert(6,'S')
                    show_dialog(
                        resultManager[count].data_id.emp_id,
                        resultManager[count].data_id.name,
                        resultManager[count].data_id.phone,
                        resultManager[count].job_id.name,
                        managerName,
                        resultManager[count].project_id.name,
                        str.toString()
                    )
                    return
                }
                count++
            }
        } else if (v!!.tag == statusBooked) {
            Toast.makeText(this, "Seat " + v.id.toString() + " is empty", Toast.LENGTH_SHORT).show()
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
