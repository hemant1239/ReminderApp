package com.example.reminderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminder = intent.getSerializableExtra("reminder") as? Reminder ?: return
        // Show notification
        NotificationHelper.showReminderNotification(context, reminder)

        // If repeatAfterHours > 0, reschedule
        if (reminder.repeatAfterHours > 0) {
            val next = reminder.copy(triggerAtMillis = reminder.triggerAtMillis + reminder.repeatAfterHours * 3600_000)
            ReminderScheduler.schedule(context, next)
            // Persist updated reminder if you maintain a list
        }
    }
}