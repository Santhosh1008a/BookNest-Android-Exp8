package com.example.exp5notification.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.exp5notification.MainActivity
import com.example.exp5notification.R

/**
 * UserSettingsActivity — Experiment 6: Basic Android Views & Experiment 8: Menus
 *
 * Demonstrates TextViews, Switches, Logout, Options Menu, and lifecycle logging.
 */
class UserSettingsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "UserSettingsActivity"
    }

    private lateinit var tvName: TextView
    private lateinit var tvUsn: TextView
    private lateinit var switchNotifications: Switch
    private lateinit var switchDarkMode: Switch
    private lateinit var switchReadingReminders: Switch
    private lateinit var tvSettingsStatus: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
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
        val name = intent.getStringExtra("USER_NAME") ?: "Sandy"
        val usn  = intent.getStringExtra("USER_USN")  ?: ""

        tvName.text = name
        tvUsn.text  = if (usn.isNotEmpty()) "USN: $usn" else "BookNest Reader"
    }

    private fun setupSwitchListeners() {
        val updateStatus = {
            val notif    = if (switchNotifications.isChecked)      "ON"  else "OFF"
            val dark     = if (switchDarkMode.isChecked)           "ON"  else "OFF"
            val reminders = if (switchReadingReminders.isChecked)  "ON"  else "OFF"
            tvSettingsStatus.text =
                "Notifications: $notif  •  Dark Mode: $dark  •  Reminders: $reminders"
        }

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this,
                "Notifications ${if (isChecked) "enabled" else "disabled"}",
                Toast.LENGTH_SHORT).show()
            updateStatus()
        }

        switchDarkMode.isChecked = (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            updateStatus()
        }

        switchReadingReminders.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this,
                "Reading Reminders ${if (isChecked) "enabled" else "disabled"}",
                Toast.LENGTH_SHORT).show()
            updateStatus()
        }
    }

    private fun setupButtonListeners() {
        findViewById<android.view.View>(R.id.btn_settings_back).setOnClickListener {
            finish()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val userName = intent.getStringExtra("USER_NAME") ?: "Sandy"
        val userUsn  = intent.getStringExtra("USER_USN")  ?: ""
        return when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_NAME", userName)
                    putExtra("USER_USN", userUsn)
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                startActivity(intent)
                true
            }
            R.id.action_browse -> {
                Toast.makeText(this, "Browse Genres on Home screen", Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            R.id.action_favorites -> {
                val favs = com.example.exp5notification.data.BookRepository.getAllBooks().filter { it.isFavorite }
                val favTitles = if (favs.isNotEmpty()) favs.joinToString { it.title } else "No favorites added yet"
                Toast.makeText(this, "Favorites: $favTitles", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_settings -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart called") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume called") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause called") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop called") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy called") }
}
