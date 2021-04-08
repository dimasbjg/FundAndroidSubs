package com.example.consumerapps.preference

import android.content.Context
import com.example.consumerapps.entity.Reminder

class ReminderPreference(context: Context) {
    companion object {
        const val PREFS_NAME = "reminder_pref"
        private const val REMINDER = "isRemind"
    }

    private val preference= context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    fun setReminder(value: Reminder) {
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.isReminderOn)
        editor.apply()

    }

    fun getReminder(): Reminder {
        val reminder = Reminder()
        reminder.isReminderOn = preference.getBoolean(REMINDER, false)
        return reminder
    }

}