package com.example.exp5notification.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.exp5notification.MainActivity
import com.example.exp5notification.R
import com.example.exp5notification.notifications.NotificationHelper

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private lateinit var notificationHelper: NotificationHelper

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            performLogin()
        } else {
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_login)

        notificationHelper = NotificationHelper(this)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val etName = findViewById<EditText>(R.id.et_name)
        val etUsn = findViewById<EditText>(R.id.et_usn)

        btnLogin.setOnClickListener {
            val name = etName.text.toString()
            val usn = etUsn.text.toString()

            if (name.isEmpty() || usn.isEmpty()) {
                Toast.makeText(this, "Please enter name and USN", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            checkPermissionAndLogin()
        }
    }

    private fun checkPermissionAndLogin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    performLogin()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            performLogin()
        }
    }

    private fun performLogin() {
        val name = findViewById<EditText>(R.id.et_name).text.toString()
        val usn = findViewById<EditText>(R.id.et_usn).text.toString()

        notificationHelper.showLoginNotification(name, usn)

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("USER_NAME", name)
            putExtra("USER_USN", usn)
        }
        startActivity(intent)
        finish()
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart called") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume called") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause called") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop called") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy called") }
}
