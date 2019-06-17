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


    var listInfo: List<HashMap<String, String>>? = null
    var listEmail: List<HashMap<String, String>>? = null
    var listCustom: List<HashMap<String, String>>? = null
    var data : String = ""
    var tokenID: String = ""
    var cusID : String = ""
    var cardID : String =""
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

        val informationEmail_1 : HashMap<String,String> = HashMap()
        informationEmail_1["email"] = email
        val informationEmail_2 : HashMap<String,String> = HashMap()
        informationEmail_2["tokenid"] = tokenId

        (listEmail as ArrayList<HashMap<String, String>>).add(informationEmail_1)
        (listEmail as ArrayList<HashMap<String, String>>).add(informationEmail_2)

        var downloadData : DownloadData1 = DownloadData1(listEmail,"https://baophuc.000webhostapp.com/createStripes.php")
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

        var downloadData1 : DownloadData1 = DownloadData1(listCustom,"https://baophuc.000webhostapp.com/getIDcard.php")
        downloadData1.execute()
        var data = downloadData1.get()
        Log.d ("DDD" , data)

        var jsonObject = JSONObject(data)
        var jsonArray = jsonObject.getJSONArray("data")
        for (i in 0 until jsonArray.length()) {

            var jsonObject = jsonArray.getJSONObject(0)
            var id: String = jsonObject.getString("id")
            Log.d("DDD", "ID: " + id )
            cardID = id
        }
    }

    fun logicGetInformation(cardInformation: Card, email: String, price: String, id: String) {

        xulitaikhoan(email,price,tokenID,"usd",id)
        Log.d("AAA","Id Customer: " + id)
        var downloadData1 : DownloadData1 = DownloadData1(listInfo,"https://baophuc.000webhostapp.com/payment.php")
        downloadData1.execute()
        data = downloadData1.get()
        Log.d("AAA",data)
        /*val stripe = Stripe(context, Constants.PUBLISHABLE_KEY)

        stripe.createToken(cardInformation, object : TokenCallback {
            override fun onSuccess(result: Token) {
                xulitaikhoan(email,price,result.id,"usd",id)
                Log.d("AAA","Id Customer: " + id)
                var downloadData1 : DownloadData1 = DownloadData1(listInfo,"https://baophuc.000webhostapp.com/payment.php")
                downloadData1.execute()
                data = downloadData1.get()
                Log.d("AAA",data)
                view.payment(cardInformation,true,result)


            }

            override fun onError(e: java.lang.Exception) {
                view.payment(cardInformation, false, null)
            }

        })*/

    }

    fun logicSendData() {

    }

    private fun xulitaikhoan(
        email: String,
        price: String,
        result: String,
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