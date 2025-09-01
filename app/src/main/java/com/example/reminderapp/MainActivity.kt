package com.example.reminderapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationHelper.createChannel(this)

        val titleEt = findViewById<EditText>(R.id.titleEt)
        val pickBtn = findViewById<Button>(R.id.pickBtn)
        val saveBtn = findViewById<Button>(R.id.saveBtn)

        var pickedMillis = System.currentTimeMillis() + 60_000 // default 1 min later

        pickBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                TimePickerDialog(this, { _, hh, mm ->
                    cal.set(y, m, d, hh, mm, 0)
                    pickedMillis = cal.timeInMillis
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        saveBtn.setOnClickListener {
            val title = titleEt.text.toString().ifBlank { "Reminder" }
            val id = System.currentTimeMillis()
            val reminder = Reminder(id, title, pickedMillis, snoozeHours = 1, repeatAfterHours = 0)
            ReminderScheduler.schedule(this, reminder)
            // Store the reminder to SharedPreferences or DB: omitted for brevity
        }
    }
}