package com.dicoding.githubuserproject.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.githubuserproject.data.local.Alarm
import com.dicoding.githubuserproject.databinding.ActivitySettingBinding
import com.dicoding.githubuserproject.model.AlarmViewModel
import com.dicoding.githubuserproject.receiver.AlarmReceiver

class SettingActivity : AppCompatActivity() {

    private lateinit var settingBinding: ActivitySettingBinding
    private lateinit var alarm: Alarm
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)

        settingBinding.buttonBack.setOnClickListener {
            val back = Intent(this, MainActivity::class.java)
            startActivity(back)
        }

        val alarmViewModel = AlarmViewModel(this)
        settingBinding.reminderToggle.isChecked = alarmViewModel.getAlarm().reminder

        alarmReceiver = AlarmReceiver()

        settingBinding.reminderToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveAlarm(true)
                alarmReceiver.setRepeatAlarm(this, "RepeatingAlarm", "09:00", "Notification")
            } else {
                saveAlarm(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveAlarm(state: Boolean) {
        val alarmViewModel = AlarmViewModel(this)
        alarm = Alarm()
        alarm.reminder = state
        alarmViewModel.setAlarm(alarm)
    }
}