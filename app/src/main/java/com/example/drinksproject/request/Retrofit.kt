package com.example.drinksproject.request

import com.example.drinksproject.responses.AlcoholicDrinksResponse
import com.example.drinksproject.responses.GeneralDrinksResponse
import com.example.drinksproject.responses.NonAlcoholicDrinksResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Request{
    val caller = Retrofit.Builder()
        .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)
}

interface ApiService {
    @GET("filter.php?a=Alcoholic")
    suspend fun getAllAlcoholicDrinks(): AlcoholicDrinksResponse
    @GET("filter.php?a=Non_Alcoholic")
    suspend fun getNonAllAlcoholicDrinks(): NonAlcoholicDrinksResponse
    @GET("lookup.php")
    suspend fun getCocktail(@Query("i") id: String): GeneralDrinksResponse
}