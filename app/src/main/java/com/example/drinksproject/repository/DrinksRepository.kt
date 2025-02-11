package com.example.drinksproject.repository

import com.example.drinksproject.request.Request
import com.example.drinksproject.responses.AlcoholicDrinksResponse
import com.example.drinksproject.responses.GeneralDrinksResponse
import com.example.drinksproject.responses.NonAlcoholicDrinksResponse

class DrinksRepository {
    suspend fun getAllAlcoholicDrinks(): AlcoholicDrinksResponse{
        return Request.caller.getAllAlcoholicDrinks()
    }

    suspend fun getAllNonAlcoholicDrinks(): NonAlcoholicDrinksResponse {
        return Request.caller.getNonAllAlcoholicDrinks()
    }

    suspend fun getCocktail(id: String): GeneralDrinksResponse{
        return Request.caller.getCocktail(id)
    }
}