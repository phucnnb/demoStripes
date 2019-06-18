package com.example.demostripes.Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.demostripes.R

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        setContentView(R.layout.activity_login)

    }
}
