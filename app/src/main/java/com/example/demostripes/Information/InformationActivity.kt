package com.example.demostripes.Information

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.demostripes.R
import com.stripe.android.model.Card
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : AppCompatActivity(), InformationView {

    private lateinit var cardInformation: Card
    private lateinit var logic: InformationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        logic = InformationPresenter(this,this)
        buttonOK()
    }

    private fun buttonOK() {
        btnOk.setOnClickListener {
            cardInformation = cardInput.card!!
            logic.logicGetInformation(cardInformation)
        }
    }

    override fun getInformationCard(cardInformation: Card, checkCard: Boolean) {
       if(cardInformation == null){
           Toast.makeText(applicationContext,"LỖI",Toast.LENGTH_SHORT).show()
       }else
           if(checkCard){
               Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
           }else {
               Toast.makeText(applicationContext,"Fail",Toast.LENGTH_SHORT).show()
           }


    }

    override fun sendCard() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
