package com.example.reminderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SnoozeReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminder = intent.getSerializableExtra("reminder") as? Reminder ?: return
        val snoozedAt = System.currentTimeMillis() + reminder.snoozeHours * 3600_000
        val snoozed = reminder.copy(triggerAtMillis = snoozedAt)
        ReminderScheduler.schedule(context, snoozed)
    }
}
