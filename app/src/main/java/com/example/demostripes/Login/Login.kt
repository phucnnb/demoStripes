package com.example.demostripes.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.demostripes.R
import com.example.demostripes.register.Register
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        setContentView(R.layout.activity_login)

        btnLoginOK.setOnClickListener {
            
        }

        tvRegister.setOnClickListener {
            val i = Intent(this,Register::class.java)
            startActivity(i)
        }

    }
}
