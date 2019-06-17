package com.example.demostripes.Information

import com.stripe.android.model.Card
import com.stripe.android.model.Token

interface InformationView {
    fun getInformationCard(
        cardInformation: Card,
        checkCard: Boolean,
        result: Token?
    )
    fun sendCard()
    fun createID(id : String)
    fun payment(cardInformation: Card, checkCard: Boolean, result: Token?)
}