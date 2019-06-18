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
import org.json.JSONObject


class InformationPresenter(private var context: Context, private var view: InformationView) :Download  {


    private var listInfo: List<HashMap<String, String>>? = null
    private var listEmail: List<HashMap<String, String>>? = null
    private var listCustom: List<HashMap<String, String>>? = null
    private var data : String = ""
    private var tokenID: String = ""
    private var cusID : String = ""
    private var cardID : String =""
    fun logicGetToken(cardInformation: Card) {
        val stripe = Stripe(context, Constants.PUBLISHABLE_KEY)
        stripe.createToken(cardInformation, object : TokenCallback {
            override fun onSuccess(result: Token) {
                tokenID = result.id
                Log.d("BBB","Token id: " + result.id)
                view.getInformationCard(cardInformation,true,result)
            }

            override fun onError(e: java.lang.Exception) {
                view.getInformationCard(cardInformation, false, null)
            }
        })
    }

    fun logicCreatAcc(email: String, tokenId: String){
        Log.d("AAA",email)
        listEmail = ArrayList()

        val informationEmail : HashMap<String,String> = HashMap()
        informationEmail["email"] = email
        val informationTokenId : HashMap<String,String> = HashMap()
        informationTokenId["tokenid"] = tokenId

        (listEmail as ArrayList<HashMap<String, String>>).add(informationEmail)
        (listEmail as ArrayList<HashMap<String, String>>).add(informationTokenId)

        val downloadData = DownloadData1(listEmail,Constants.URL_CUSTOMER_ID)
        downloadData.execute()
        data = downloadData.get()
        cusID = data
        view.createID(data)
        Log.d("BBB",data)
    }

    fun logicGetIDcard() {
        listCustom = ArrayList()
        val idCustom : HashMap<String,String> = HashMap()
        idCustom["customer"] = cusID

        (listCustom as ArrayList<HashMap<String, String>>).add(idCustom)

        val downloadData1 = DownloadData1(listCustom,Constants.URL_CARD_ID)
        downloadData1.execute()
        val data = downloadData1.get()
        Log.d ("DDD" , data)

        val jsonObject = JSONObject(data)
        val jsonArray = jsonObject.getJSONArray("data")
        for (i in 0 until jsonArray.length()) {

            val jsonObject1 = jsonArray.getJSONObject(0)
            val id: String = jsonObject1.getString("id")
            Log.d("DDD", "ID: $id")
            cardID = id
        }
    }

    fun logicGetInformation(email: String, price: String, id: String) {

        xulitaikhoan(email,price,tokenID,"usd")
        Log.d("AAA", "Id Customer: $id")
        val downloadData1 = DownloadData1(listInfo,Constants.URL_PAYMENT)
        downloadData1.execute()
        data = downloadData1.get()
        Log.d("AAA",data)
           }

    private fun xulitaikhoan(
        email: String,
        price: String,
        //result: String,
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

    override fun download(s: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




}