package com.example.exp5notification.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.exp5notification.R

/**
 * WebViewActivity — Experiment 8: Implement Menus and WebView
 *
 * Displays free reading resources securely in-app using Android WebView.
 * Includes lifecycle logging, WebViewClient, progress tracking, and back navigation.
 */
class WebViewActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WebViewActivity"
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TITLE = "extra_title"
    }

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvTitle: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnRefresh: ImageButton

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_webview)

        webView = findViewById(R.id.webview_reader)
        progressBar = findViewById(R.id.progress_webview)
        tvTitle = findViewById(R.id.tv_webview_title)
        btnBack = findViewById(R.id.btn_webview_back)
        btnRefresh = findViewById(R.id.btn_webview_refresh)

        val url = intent.getStringExtra(EXTRA_URL) ?: "https://www.gutenberg.org"
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Book Reader"

        tvTitle.text = title

        // Configure WebView settings securely
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
                Log.d(TAG, "Page started loading: $url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
                Log.d(TAG, "Page finished loading: $url")
            }

            override fun onReceivedError(
                view: WebView?,
                request: android.webkit.WebResourceRequest?,
                error: android.webkit.WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                progressBar.visibility = View.GONE
                Toast.makeText(this@WebViewActivity, "Failed to load resource safely.", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "WebView error: ${error?.description}")
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                }
            }
        }

        // Load the URL
        webView.loadUrl(url)

        // Button Listeners
        btnBack.setOnClickListener {
            handleBackNavigation()
        }

        btnRefresh.setOnClickListener {
            webView.reload()
            Toast.makeText(this, "Refreshing page...", Toast.LENGTH_SHORT).show()
        }

        // Handle Back Press using OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackNavigation()
            }
        })
    }

    private fun handleBackNavigation() {
        if (webView.canGoBack()) {
            webView.goBack()
            Log.d(TAG, "WebView went back in history")
        } else {
            finish()
            Log.d(TAG, "WebView finished, closing activity")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
        webView.onPause()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
        webView.destroy()
    }
}
