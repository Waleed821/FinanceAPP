package com.example.financialapp.activities.retrofit

import com.example.financialapp.activities.models.BalanceResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @GET("balance.php")
    fun getBalance(): Call<BalanceResponse?>?

    @FormUrlEncoded
    @POST("updateBalance.php")
    fun updateBalance(
        @Field("balance") balance: Int?
    ): Call<BalanceResponse?>?

}