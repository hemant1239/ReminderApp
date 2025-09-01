package com.example.reminderapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
    private const val CHANNEL_ID = "reminder_channel"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val ch = NotificationChannel(CHANNEL_ID, "Reminders", NotificationManager.IMPORTANCE_HIGH)
            nm.createNotificationChannel(ch)
        }
    }

    fun showReminderNotification(context: Context, reminder: Reminder) {
        createChannel(context)

        val openIntent = Intent(context, MainActivity::class.java)
        val openPI = PendingIntent.getActivity(context, reminder.id.toInt(), openIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Snooze action
        val snoozeIntent = Intent(context, SnoozeReceiver::class.java).apply { putExtra("reminder", reminder) }
        val snoozePI = PendingIntent.getBroadcast(context, (reminder.id + 1).toInt(), snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Reminder: ${reminder.title}")
            .setContentText("Tap to open. Snooze available.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(openPI)
            .addAction(android.R.drawable.ic_media_pause, "Snooze", snoozePI)
            .setAutoCancel(true)
            .build()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(reminder.id.toInt(), notif)
    }
}
