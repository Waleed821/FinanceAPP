package com.example.financialapp.activities.models

import com.google.gson.annotations.SerializedName


data class BalanceResponse(
    @SerializedName("balance")
    val balance: Double
)