package com.example.demostripes.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.demostripes.Constants
import com.example.demostripes.Download.DownloadData1
import com.example.demostripes.Payment.PaymentActivity
import com.example.demostripes.R
import com.example.demostripes.Register.Register

import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class Login : AppCompatActivity() {
        private var account : String = ""
        private var password : String = ""
        private lateinit var sharePre: SharedPreferences
    private var listUser : List<HashMap<String, String>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        setContentView(R.layout.activity_login)

        sharePre = getSharedPreferences("sharePre", Context.MODE_PRIVATE)


        // Click Button Login
        btnLoginOK.setOnClickListener {
            account = editLoginAcc.text.toString()
            password = editLoginPass.text.toString()

            prepareList()

            val loginAccount = DownloadData1 (listUser,Constants.URL_LOGIN)   //call API for check account
            loginAccount.execute()
            val data = loginAccount.get()
            val jsonObject = JSONObject(data)
            val check : String = jsonObject.getString("ketqua")

            // check account
            if(check == "1"){
                Toast.makeText(applicationContext,"Thành Công",Toast.LENGTH_SHORT).show()

                val editor = sharePre.edit()
                editor.putString(Constants.ACCOUNT, account)  //save account
                editor.putBoolean(Constants.CHECK_LOGIN, true) // save check login
                editor.commit()

                val i = Intent(this,PaymentActivity::class.java)
                startActivity(i)
            }else {
                Toast.makeText(applicationContext,"Thất Bại",Toast.LENGTH_SHORT).show()
            }
        }

        // Click textview Register
        tvRegister.setOnClickListener {
            val editor = sharePre.edit()
            editor.putBoolean(Constants.CHECK_LOGIN, false)
            editor.commit()
            val i = Intent(this, Register::class.java)
            startActivity(i)
        }

    }

    // prepare listHashmap include account and password for Login
    private fun prepareList() {
        listUser = ArrayList()

        val itemAccount : HashMap<String,String> = HashMap()
        itemAccount["account"] = account
        val itemPassword : HashMap<String,String> = HashMap()
        itemPassword["pw"] = password

        (listUser as ArrayList<HashMap<String, String>>).add(itemAccount)
        (listUser as ArrayList<HashMap<String, String>>).add(itemPassword)
    }
}
