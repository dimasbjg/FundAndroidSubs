package com.example.fundandroidsubs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.fundandroidsubs.R
import com.example.fundandroidsubs.databinding.ActivitySettingBinding
import com.example.fundandroidsubs.entity.Reminder
import com.example.fundandroidsubs.preference.ReminderPreference
import com.example.fundandroidsubs.reciever.AlarmReceiver

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Setting"

        actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.favorites -> {
                    val intent = Intent(this, FavoritesListActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "My Favorites", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


        val reminderPreference = ReminderPreference(this)
        binding.switchReminder.isChecked = reminderPreference.getReminder().isReminderOn

        alarmReceiver = AlarmReceiver()

        binding.switchReminder.setOnCheckedChangeListener { compoundButton, state ->
            if (state) {
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(this, "RepeatingAlarm" , "09:00", "Github Reminder")
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }

    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()
        reminder.isReminderOn = state
        reminderPreference.setReminder(reminder)
    }
}