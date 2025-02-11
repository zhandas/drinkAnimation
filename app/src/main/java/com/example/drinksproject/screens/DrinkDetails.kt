package com.example.drinksproject.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.drinksproject.data.DrinkData
import com.example.drinksproject.viewmodel.DrinksViewModel

@Composable
fun DrinkDetail(viewModel: DrinksViewModel){
    val cocktailState by viewModel.cocktailState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val drinkId by viewModel.drinkToOpenId.collectAsState()

    LaunchedEffect(drinkId) {
        viewModel.getCocktail()
    }

    if (isLoading){
        LoadingScreen()
    }
    else if (cocktailState.error != null){
        ErrorScreen(cocktailState.error!!)
    }
    else if (cocktailState.result.isNotEmpty()){
        DrinkScreen(cocktailState.result[0])
    }
}

@Composable
fun DrinkScreen(drinkData: DrinkData) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 700)) +
                scaleIn(animationSpec = tween(durationMillis = 700))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp),
                model = drinkData.strDrinkThumb,
                contentDescription = drinkData.strDrink
            )
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Name: ${drinkData.strDrink}",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "IBA: ${drinkData.strIBA}",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Glass: ${drinkData.strGlass}",
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Category: ${drinkData.strCategory}",
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Instruction",
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = drinkData.strInstructions,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
