package com.example.demostripes.Information

import com.stripe.android.model.Card

interface InformationView {
    fun getInformationCard(cardInformation: Card, checkCard: Boolean)
    fun sendCard()
}