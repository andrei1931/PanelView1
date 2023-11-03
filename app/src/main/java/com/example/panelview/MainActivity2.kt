package com.example.panelview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val sensorListView = findViewById<ListView>(R.id.sensorListView)

        val sensorDataList = listOf(
            SensorData("Nume Senzor 1", "Valoare 1"),
            SensorData("Nume Senzor 2", "Valoare 2"),
        )
        val sensorAdapter = SensorAdapter(this, sensorDataList)
        sensorListView.adapter = sensorAdapter
    }
    data class SensorData(val name: String, val value: String)
}
class SensorAdapter(context: Context, data: List<MainActivity2.SensorData>) :
    ArrayAdapter<MainActivity2.SensorData>(context, 0, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val sensorData = getItem(position)
        var rowView = convertView

        if (rowView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.list_item, parent, false)
        }

        val sensorName = rowView?.findViewById<TextView>(R.id.sensorName)
        val sensorValue = rowView?.findViewById<TextView>(R.id.sensorValue)

        sensorName?.text = sensorData?.name
        sensorValue?.text = sensorData?.value

        return rowView!!
    }
}
