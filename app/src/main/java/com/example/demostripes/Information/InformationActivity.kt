package com.example.demostripes.Information

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.demostripes.Download.DownloadData1
import com.example.demostripes.R
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import kotlinx.android.synthetic.main.activity_information.*
import org.json.JSONObject

class InformationActivity : AppCompatActivity(), InformationView {


    private lateinit var cardInformation: Card
    private lateinit var logic: InformationPresenter
    private var email : String = ""
    private var price : String = ""
    private var idCus : String = ""
    private var tokenId : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        logic = InformationPresenter(this,this)
        buttonGetToken()
        buttonOK()
        buttonCreate()
        buttonParse()
    }

    private fun buttonParse() {
        var s : String = "{\"object\":\"list\",\"data\":[{\"id\":\"card_1EmFrWAWnzXOI7ehEWD7J9aC\",\"object\":\"card\",\"address_city\":null,\"address_country\":null,\"address_line1\":null,\"address_line1_check\":null,\"address_line2\":null,\"address_state\":null,\"address_zip\":null,\"address_zip_check\":null,\"brand\":\"Visa\",\"country\":\"US\",\"customer\":\"cus_FGnSo8aLDrfUuI\",\"cvc_check\":\"pass\",\"dynamic_last4\":null,\"exp_month\":12,\"exp_year\":2020,\"fingerprint\":\"vHz2mkuJHctz0190\",\"funding\":\"credit\",\"last4\":\"4242\",\"metadata\":[],\"name\":null,\"tokenization_method\":null}],\"has_more\":false,\"total_count\":1,\"url\":\"\\/v1\\/customers\\/cus_FGnSo8aLDrfUuI\\/sources\"}"

        btnParse.setOnClickListener {
            logic.logicGetIDcard()
        }
    }

    private fun buttonGetToken() {
        btnGetToken.setOnClickListener {
            cardInformation = cardInput.card!!
            logic.logicGetToken(cardInformation)
        }
    }

    private fun buttonCreate() {
        btnCreate.setOnClickListener {
            tokenId = txtTokenID.text.toString()
            email = editEmail.text.toString()
            logic.logicCreatAcc(email,tokenId)
        }
    }

    private fun buttonOK() {
        btnOk.setOnClickListener {
            cardInformation = cardInput.card!!
            email = editEmail.text.toString()
            price = editPrice.text.toString()
            idCus = txtIDcus.text.toString()
            logic.logicGetInformation(cardInformation,email,price,idCus)

        }
    }

    override fun createID(id: String) {
        txtIDcus.text = id
    }

    override fun getInformationCard(cardInformation: Card, checkCard: Boolean, result: Token?) {
       if(cardInformation == null){
           Toast.makeText(applicationContext,"LỖI",Toast.LENGTH_SHORT).show()
       }else
           if(checkCard){
               Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()

               txtTokenID.text = result!!.id
           }else {
               Toast.makeText(applicationContext,"Fail",Toast.LENGTH_SHORT).show()
           }


    }

    override fun payment (cardInformation: Card, checkCard: Boolean, result: Token?) {
        if(cardInformation == null){
            Toast.makeText(applicationContext,"LỖI",Toast.LENGTH_SHORT).show()
        }else
            if(checkCard){
                Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()

                txtToken.text = result!!.id
            }else {
                Toast.makeText(applicationContext,"Fail",Toast.LENGTH_SHORT).show()
            }


    }

    override fun sendCard() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
