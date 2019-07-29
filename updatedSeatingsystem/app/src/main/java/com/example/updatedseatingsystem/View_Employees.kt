package com.example.updatedseatingsystem

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_view__employees.*

class View_Employees : AppCompatActivity() , View.OnClickListener{

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
    //////

    /////////////
    override fun onClick(v: View?) {
        if (v!!.tag == statusAvailable) {
            Toast.makeText(this, "Seat " + v.id.toString(), Toast.LENGTH_SHORT).show()
        } else if (v!!.tag == statusBooked) {
            Toast.makeText(this, "Seat " + v.id.toString() + " is empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view__employees)


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
                count++
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
            } else if (seats[index] == 'A') {
                count++
                var view: TextView = TextView(this)
                var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.setLayoutParams(layoutParams)
                view.setPadding(0, 0, 0, 2 * seatGaping)
                view.setId(count)
                view.setGravity(Gravity.CENTER)
                view.setBackgroundResource(R.drawable.green)
                // view.setTextColor(Color.WHITE)
                view.setTag(statusAvailable)
                // view.setText(count + "")
                // view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9)
                linLayout!!.addView(view)
                seatViewList.add(view)
                view.setOnClickListener(this)
            } else if (seats[index] == '_') {
                var view: TextView = TextView(this)
                var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.setLayoutParams(layoutParams)
                view.setBackgroundColor(Color.TRANSPARENT)
                linLayout!!.addView(view)
            }
        }

        ///////
        //spiner
        val manager_Array = arrayOf("Ahmed Hussien","Amr Samy", "Mohamed Sofy", "Dania Alaaeldin ", "Ammar Sayed")
        val project_Array = arrayOf("hiemdal", "thor", "Seating System")
        val filter_Array = arrayOf("Projects", "Managers")
        sp_filter.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filter_Array)
        sp_manager_project.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, project_Array)

        sp_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0){
                    sp_manager_project.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, project_Array)
                }else{
                    sp_manager_project.adapter =
                        ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, manager_Array)

                }
            }

        }



    }
}
