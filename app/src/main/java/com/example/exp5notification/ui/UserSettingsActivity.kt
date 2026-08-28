package com.example.exp5notification.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.exp5notification.R

/**
 * UserSettingsActivity — Experiment 6: Basic Android Views
 *
 * Demonstrates:
 *  - TextView  (name, USN display)
 *  - Switch    (Notifications, Dark Mode, Reading Reminders)
 *  - Button    (Logout with proper Activity back-stack clearing)
 *
 * User info is passed via Intent extras from BookDetailsActivity.
 * Logout navigates back to LoginActivity with FLAG_ACTIVITY_CLEAR_TASK
 * so the back button cannot return to the app.
 */
class UserSettingsActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvUsn: TextView
    private lateinit var switchNotifications: Switch
    private lateinit var switchDarkMode: Switch
    private lateinit var switchReadingReminders: Switch
    private lateinit var tvSettingsStatus: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        bindViews()
        populateUserInfo()
        setupSwitchListeners()
        setupButtonListeners()
    }

    private fun bindViews() {
        tvName               = findViewById(R.id.tv_settings_name)
        tvUsn                = findViewById(R.id.tv_settings_usn)
        switchNotifications  = findViewById(R.id.switch_notifications)
        switchDarkMode       = findViewById(R.id.switch_dark_mode)
        switchReadingReminders = findViewById(R.id.switch_reading_reminders)
        tvSettingsStatus     = findViewById(R.id.tv_settings_status)
        btnLogout            = findViewById(R.id.btn_logout)
    }

    private fun populateUserInfo() {
        // Retrieve user info passed from LoginActivity through the stack
        val name = intent.getStringExtra("USER_NAME") ?: "Sandy"
        val usn  = intent.getStringExtra("USER_USN")  ?: ""

        tvName.text = name
        tvUsn.text  = if (usn.isNotEmpty()) "USN: $usn" else "BookNest Reader"
    }

    private fun setupSwitchListeners() {
        // Helper to refresh the status summary line whenever any switch changes
        val updateStatus = {
            val notif    = if (switchNotifications.isChecked)      "ON"  else "OFF"
            val dark     = if (switchDarkMode.isChecked)           "ON"  else "OFF"
            val reminders = if (switchReadingReminders.isChecked)  "ON"  else "OFF"
            tvSettingsStatus.text =
                "Notifications: $notif  •  Dark Mode: $dark  •  Reminders: $reminders"
        }

        // Switch 1: Notifications — setOnCheckedChangeListener
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this,
                "Notifications ${if (isChecked) "enabled" else "disabled"}",
                Toast.LENGTH_SHORT).show()
            updateStatus()
        }

        // Set initial dark mode switch state
        switchDarkMode.isChecked = (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)

        // Switch 2: Dark Mode — setOnCheckedChangeListener
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            updateStatus()
        }

        // Switch 3: Reading Reminders — setOnCheckedChangeListener
        switchReadingReminders.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this,
                "Reading Reminders ${if (isChecked) "enabled" else "disabled"}",
                Toast.LENGTH_SHORT).show()
            updateStatus()
        }
    }

    private fun setupButtonListeners() {
        // Back button
        findViewById<android.view.View>(R.id.btn_settings_back).setOnClickListener {
            finish()
        }

        // Logout button — clears entire back stack, returns to LoginActivity
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()   // also finish this activity explicitly
        }
    }
}
