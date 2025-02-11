package com.example.drinksproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinksproject.data.DrinkData
import com.example.drinksproject.repository.DrinksRepository
import com.example.drinksproject.states.AlcoholicDrinksState
import com.example.drinksproject.states.GeneralDrinksState
import com.example.drinksproject.states.NonAlcoholicDrinksState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val AlcoholicDrinksIndex = 0
const val NonAlcoholicDrinksIndex = 1

class DrinksViewModel: ViewModel() {

    private val _repository = DrinksRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    private val _alcoholicDrinksState = MutableStateFlow(AlcoholicDrinksState())
    val alcoholicDrinksState get()= _alcoholicDrinksState.asStateFlow()

    private val _nonAlcoholicDrinksState = MutableStateFlow(NonAlcoholicDrinksState())
    val nonAlcoholicDrinksState get()= _nonAlcoholicDrinksState.asStateFlow()

    private val _chosenStateIndex = MutableStateFlow(AlcoholicDrinksIndex)
    val chosenStateIndex = _chosenStateIndex.asStateFlow()

    private val _cocktailToFind =MutableStateFlow("")
    val cocktailToFind = _cocktailToFind.asStateFlow()
    private val _resultOfFindings = MutableStateFlow(listOf<DrinkData>())
    val resultOfFindings = _resultOfFindings.asStateFlow()

    private val _drinkToOpenId = MutableStateFlow("")
    val drinkToOpenId = _drinkToOpenId.asStateFlow()
    private val _cocktailState = MutableStateFlow(GeneralDrinksState())
    val cocktailState = _cocktailState.asStateFlow()

    fun setDrinkToOpen(id: String){
        _drinkToOpenId.value = id
    }

    fun setCocktailToFind(name: String){
        _cocktailToFind.value = name
    }

    fun getCocktailsToFind(list: List<DrinkData>){
        _resultOfFindings.value = list.filter{ drink -> drink.strDrink.startsWith(_cocktailToFind.value, true) }
    }

    fun setChosenStateIndex(index: Int){
        _chosenStateIndex.value = index
        if (_chosenStateIndex.value == AlcoholicDrinksIndex){
            if (_alcoholicDrinksState.value.result.isEmpty()){
                getAllAlcoholicDrinks()
            }
        }
        if (_chosenStateIndex.value == NonAlcoholicDrinksIndex){
            if (_nonAlcoholicDrinksState.value.result.isEmpty()){
                getNonAllAlcoholicDrinks()
            }
        }
    }

    fun getCocktail(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = _repository.getCocktail(_drinkToOpenId.value)
                _cocktailState.value = _cocktailState.value.copy(
                    result = result.drinks
                )
            }
            catch (e: Exception){
                _cocktailState.value = _cocktailState.value.copy(
                    error = e.message
                )
            }
            finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllAlcoholicDrinks(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = _repository.getAllAlcoholicDrinks()
                _alcoholicDrinksState.value = _alcoholicDrinksState.value.copy(
                    result = result.drinks
                )
            }
            catch (e: Exception){
                _alcoholicDrinksState.value = _alcoholicDrinksState.value.copy(
                    error = e.message
                )
            }
            finally {
                _isLoading.value = false
            }
        }
    }

    fun getNonAllAlcoholicDrinks(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = _repository.getAllNonAlcoholicDrinks()
                _nonAlcoholicDrinksState.value = _nonAlcoholicDrinksState.value.copy(
                    result = result.drinks
                )
            }
            catch (e: Exception){
                _nonAlcoholicDrinksState.value = _nonAlcoholicDrinksState.value.copy(
                    error = e.message
                )
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}