package com.example.exp5notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.exp5notification.ui.HomeFragment
import com.example.exp5notification.ui.UserSettingsActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
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
                true
            }
            R.id.action_browse -> {
                android.widget.Toast.makeText(this, "Browse Genres on Home screen", android.widget.Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_favorites -> {
                val favs = com.example.exp5notification.data.BookRepository.getAllBooks().filter { it.isFavorite }
                val favTitles = if (favs.isNotEmpty()) favs.joinToString { it.title } else "No favorites added yet"
                android.widget.Toast.makeText(this, "Favorites: $favTitles", android.widget.Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, UserSettingsActivity::class.java).apply {
                    putExtra("USER_NAME", userName)
                    putExtra("USER_USN", userUsn)
                }
                startActivity(intent)
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
