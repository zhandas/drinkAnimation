package com.example.drinksproject.states

import com.example.drinksproject.data.DrinkData

data class AlcoholicDrinksState(
    var result: List<DrinkData> = listOf(),
    var error: String? = null
)

data class NonAlcoholicDrinksState(
    var result: List<DrinkData> = listOf(),
    var error: String? = null
)

data class GeneralDrinksState(
    var result: List<DrinkData> = listOf(),
    var error: String? = null
)