package com.example.drinksproject.responses

import com.example.drinksproject.data.DrinkData

data class AlcoholicDrinksResponse(
    val drinks: List<DrinkData>
)

data class NonAlcoholicDrinksResponse(
    val drinks: List<DrinkData>
)

data class GeneralDrinksResponse(
    val drinks: List<DrinkData>
)