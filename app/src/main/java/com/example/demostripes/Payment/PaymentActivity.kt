package com.example.demostripes.Payment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import android.widget.Toast
import com.example.demostripes.Constants
import com.example.demostripes.Download.DownloadData1
import com.example.demostripes.R
import com.example.demostripes.Register.AddCard
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONObject


class PaymentActivity : AppCompatActivity() {
    private lateinit var sharePre: SharedPreferences
    private var account: String = ""
    private var idcard : String = ""
    private var idcustomer: String = ""
    private var listInfo: List<HashMap<String, String>>? = null
    private var listAccount: List<HashMap<String, String>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        sharePre = getSharedPreferences("sharePre", Context.MODE_PRIVATE)
        account = sharePre.getString(Constants.ACCOUNT,"")      //get account logged

        listAccount = ArrayList()

        val informationEmail : HashMap<String,String> = HashMap()
        informationEmail["email"] = account

        (listAccount as ArrayList<HashMap<String, String>>).add(informationEmail)
        val loadID = DownloadData1(listAccount,Constants.URL_LOAD_ID)       //Call API get Card ID and Customer ID  of account logged
        loadID.execute()
        val data = loadID.get()
        loadIDaccount(data)

        // Click buy
        //Have you checked your existing card?
        btnBuy.setOnClickListener {
            if(idcard.equals("none")){
                Toast.makeText(applicationContext,"Bạn chưa có thẻ",Toast.LENGTH_SHORT).show()
                val i = Intent(this,AddCard::class.java)
                startActivity(i)
            }else {
                xulitaikhoan(account,"100",idcard,"usd",idcustomer)

                val downloadData1 = DownloadData1(listInfo,Constants.URL_PAYMENT)
                downloadData1.execute()
                val data = downloadData1.get()
                Log.d("AAA",data)
                Toast.makeText(applicationContext,"Payment has been charged!!!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadIDaccount(get: String) {
        val jsonObject = JSONObject(get)
        idcard  = jsonObject.getString("idcard")
        idcustomer  = jsonObject.getString("idcustomer")
    }

    private fun xulitaikhoan(
        email: String,
        price: String,
        cardID: String,
        s2: String,
        id: String
    ) {
        listInfo = ArrayList()
        val information1 : HashMap<String,String> = HashMap()
        information1["description"] = email

        val information2 : HashMap<String,String> = HashMap()
        information2["amount"] = price

        val information3 : HashMap<String,String> = HashMap()
        information3["cardID"] = cardID

        val information4 : HashMap<String,String> = HashMap()
        information4["currency"] = s2

        val information5 : HashMap<String,String> = HashMap()
        information5["customer"] = id

        (listInfo as ArrayList<HashMap<String, String>>).add(information1)
        (listInfo as ArrayList<HashMap<String, String>>).add(information2)
        (listInfo as ArrayList<HashMap<String, String>>).add(information3)
        (listInfo as ArrayList<HashMap<String, String>>).add(information4)
        (listInfo as ArrayList<HashMap<String, String>>).add(information5)
    }
}
