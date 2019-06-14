package com.example.demostripes.Information

import android.content.Context
import com.example.demostripes.Constants
import com.stripe.android.model.Card
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Token


class InformationPresenter(private var context: Context, private var view: InformationView) {


    fun logicGetInformation(cardInformation: Card) {

        val stripe = Stripe(context, Constants.PUBLISHABLE_KEY)

        stripe.createToken(cardInformation, object : TokenCallback {
            override fun onSuccess(result: Token) {
                view.getInformationCard(cardInformation,true)
            }

            override fun onError(e: java.lang.Exception) {
                view.getInformationCard(cardInformation,false)
            }

        })

    }

    fun logicSendData() {

    }
}