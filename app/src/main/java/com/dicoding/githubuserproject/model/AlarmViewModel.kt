package com.dicoding.githubuserproject.model

import android.content.Context
import com.dicoding.githubuserproject.data.local.Alarm

class AlarmViewModel(context: Context) {
    companion object {
        const val PREFERENCE_ALARM = "preference_alarm"
        private const val ALARM = "alarm"
    }

    private val preference = context.getSharedPreferences(PREFERENCE_ALARM, Context.MODE_PRIVATE)

    fun setAlarm(value: Alarm) {
        val edit = preference.edit()
        edit.putBoolean(ALARM, value.reminder)
        edit.apply()
    }

    fun getAlarm(): Alarm{
        val model = Alarm()
        model.reminder = preference.getBoolean(ALARM, false)
        return model
    }
}