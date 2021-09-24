package com.bishaljung.vetementsfashionnepal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bishaljung.vetementsfashionnepal.R

class AboutPageActivity : AppCompatActivity() {


    //////------------about page codes---------///////
    private lateinit var webviewSoftwarica: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_page)

        webviewSoftwarica=findViewById(R.id.webviewSoftwarica)
        val webViewClient = WebViewClient()
        webviewSoftwarica.webViewClient=webViewClient
        webviewSoftwarica.loadUrl("https://softwarica.edu.np");
    }
}