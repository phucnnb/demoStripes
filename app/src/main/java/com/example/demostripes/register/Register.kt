package com.example.demostripes.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.demostripes.Constants
import com.example.demostripes.Download.DownloadData1
import com.example.demostripes.R
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private var account : String = ""
    private var password : String = ""
    private var listUser : List<HashMap<String, String>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegisterOK.setOnClickListener {
            account = editRegisterAcc.text.toString()
            password = editRegisterPass.text.toString()

            prepareList()

            val checkAccount = DownloadData1(listUser,Constants.URL_CHECK_USER)
            checkAccount.execute()
            val check = checkAccount.get()

            if(check == "0"){
                Toast.makeText(applicationContext,"Tài Khoản Đã Được Đăng Kí",Toast.LENGTH_SHORT).show()
            }else{

                val downloadData1 = DownloadData1(listUser,Constants.URL_REGISTER)
                downloadData1.execute()
                val data = downloadData1.get()
                if (data == "1"){
                    Toast.makeText(applicationContext,"Đăng Kí Thành Công",Toast.LENGTH_SHORT).show()
                    val i = Intent(this,AddCard::class.java )
                    startActivity(i)
                }else{
                    Toast.makeText(applicationContext,"FAIL",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

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
