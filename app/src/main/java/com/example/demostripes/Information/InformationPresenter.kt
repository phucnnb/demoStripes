package com.example.demostripes.Information

import android.content.Context
import android.util.Log
import com.example.demostripes.Constants
import com.example.demostripes.Download.Download
import com.example.demostripes.Download.DownloadData1
import com.stripe.android.model.Card
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Token




class InformationPresenter(private var context: Context, private var view: InformationView) :Download  {


    var listInfo: List<HashMap<String, String>>? = null
    var data : String = ""

    fun logicGetInformation(cardInformation: Card, email: String, price: String) {

        val stripe = Stripe(context, Constants.PUBLISHABLE_KEY)

        stripe.createToken(cardInformation, object : TokenCallback {
            override fun onSuccess(result: Token) {
                xulitaikhoan(email,price,result.id,"usd")
                var downloadData1 : DownloadData1 = DownloadData1(listInfo,"https://baophuc.000webhostapp.com/payment.php")
                downloadData1.execute()
                data = downloadData1.get()
                Log.d("AAA",data)
                view.getInformationCard(cardInformation,true,result)


            }

            override fun onError(e: java.lang.Exception) {
                view.getInformationCard(cardInformation, false, null)
            }

        })

    }

    fun logicSendData() {

    }

    private fun xulitaikhoan(email: String, price: String, result: String, s2: String) {
        listInfo = ArrayList()
        val information1 : HashMap<String,String> = HashMap()
        information1["description"] = email

        val information2 : HashMap<String,String> = HashMap()
        information2["amount"] = price

        val information3 : HashMap<String,String> = HashMap()
        information3["stripeToken"] = result

        val information4 : HashMap<String,String> = HashMap()
        information4["currency"] = s2

        (listInfo as ArrayList<HashMap<String, String>>).add(information1)
        (listInfo as ArrayList<HashMap<String, String>>).add(information2)
        (listInfo as ArrayList<HashMap<String, String>>).add(information3)
        (listInfo as ArrayList<HashMap<String, String>>).add(information4)
    }


    override fun download(s: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}