package com.example.demostripes.Register


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.demostripes.Constants
import com.example.demostripes.Download.DownloadData1
import com.example.demostripes.Login.Login
import com.example.demostripes.Payment.PaymentActivity
import com.example.demostripes.R
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Token
import kotlinx.android.synthetic.main.activity_add_card.*
import org.json.JSONObject

class AddCard : AppCompatActivity() {

    private var account : String = ""
    private var tokenId : String = ""
    private var customerId : String = ""
    private var cardID : String = ""
    private var checkLogin : Boolean = false
    private var listAccount: List<HashMap<String, String>>? = null
    private var listCustom: List<HashMap<String, String>>? = null
    private var listCardId: List<HashMap<String, String>>? = null
    private lateinit var sharePre: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        sharePre = getSharedPreferences("sharePre", Context.MODE_PRIVATE)
        account = sharePre.getString(Constants.ACCOUNT,"")
        checkLogin = sharePre.getBoolean(Constants.CHECK_LOGIN,false)


        btnAddCard.setOnClickListener {
            val stripe = Stripe(this, Constants.PUBLISHABLE_KEY)
            stripe.createToken(cardAddCard.card!!, object : TokenCallback {
                override fun onSuccess(result: Token) {
                    tokenId = result.id
                    createAccountStripe()
                    getCardId()
                    addCard()
                }

                override fun onError(e: java.lang.Exception) {
                    Toast.makeText(applicationContext,"FAIL",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun createAccountStripe() {
        listAccount = ArrayList()

        val informationEmail : HashMap<String,String> = HashMap()
        informationEmail["email"] = account
        val informationTokenId : HashMap<String,String> = HashMap()
        informationTokenId["tokenid"] = tokenId

        (listAccount as ArrayList<HashMap<String, String>>).add(informationEmail)
        (listAccount as ArrayList<HashMap<String, String>>).add(informationTokenId)

        val getCustomerID = DownloadData1(listAccount,Constants.URL_CUSTOMER_ID)
        getCustomerID.execute()
        val data = getCustomerID.get()
        customerId = data
        Log.d("EEE", "Card ID: $customerId")

    }

    private fun getCardId() {
        listCustom = ArrayList()
        val idCustom : HashMap<String,String> = HashMap()
        idCustom["customer"] = customerId

        (listCustom as ArrayList<HashMap<String, String>>).add(idCustom)

        val getIdCard = DownloadData1(listCustom,Constants.URL_CARD_ID)
        getIdCard.execute()
        val data = getIdCard.get()

        val jsonObject = JSONObject(data)
        val jsonArray = jsonObject.getJSONArray("data")
        for (i in 0 until jsonArray.length()) {
            val jsonObject1 = jsonArray.getJSONObject(0)
            val id: String = jsonObject1.getString("id")
            cardID = id
            Log.d("EEE", "Card ID: $cardID")
        }
    }

    private fun addCard() {
        listCardId = ArrayList()

        val informationAcc : HashMap<String,String> = HashMap()
        informationAcc["account"] = account
        val informationCardId : HashMap<String,String> = HashMap()
        informationCardId["idcard"] = cardID
        val informationCustomerId : HashMap<String,String> = HashMap()
        informationCustomerId["idcustomer"] = customerId

        (listCardId as ArrayList<HashMap<String, String>>).add(informationAcc)
        (listCardId as ArrayList<HashMap<String, String>>).add(informationCardId)
        (listCardId as ArrayList<HashMap<String, String>>).add(informationCustomerId)

        val updateCardId = DownloadData1(listCardId,Constants.URL_UPDATE_CARD_ID)
        updateCardId.execute()
        val data = updateCardId.get()
        if (data == "1"){
            Toast.makeText(applicationContext,"Add Card Success",Toast.LENGTH_SHORT).show()
            if (checkLogin){
                Log.d("JJJ",listAccount.toString())
                val loadID = DownloadData1(listAccount,Constants.URL_LOAD_ID)
                loadID.execute()
                val data = loadID.get()
                Log.d("JJJ",data)
                loadIDaccount(data)
                val i = Intent(this, PaymentActivity::class.java)
                startActivity(i)
            }else{
                val i = Intent(this, Login::class.java)
                startActivity(i)
            }
        }else{
            Toast.makeText(applicationContext,"Fail",Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadIDaccount(get: String) {
        val jsonObject = JSONObject(get)
        val idcard : String = jsonObject.getString("idcard")
        val idcustomer : String = jsonObject.getString("idcustomer")
        val editor = sharePre.edit()
        editor.putString(Constants.CARD_ID, idcard)
        editor.putString(Constants.CUSTOMER_ID, idcustomer)
        editor.commit()
    }
}
