package com.example.reminderapp

import java.io.Serializable

data class Reminder(
    val id: Long,
    val title: String,
    val triggerAtMillis: Long,
    val snoozeHours: Int, // when snoozed, snooze this many hours
    val repeatAfterHours: Int // after reminder triggers, reschedule after these hours (0 = no repeat)
): Serializable
