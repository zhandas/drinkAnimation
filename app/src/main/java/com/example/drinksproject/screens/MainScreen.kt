package com.example.drinksproject.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.drinksproject.data.DrinkData
import com.example.drinksproject.viewmodel.AlcoholicDrinksIndex
import com.example.drinksproject.viewmodel.DrinksViewModel
import com.example.drinksproject.viewmodel.NonAlcoholicDrinksIndex

@Composable
fun MainScreen(viewModel: DrinksViewModel, navHostController: NavHostController){
    val isLoading by viewModel.isLoading.collectAsState()
    val alcoholicDrinksState by viewModel.alcoholicDrinksState.collectAsState()
    val nonAlcoholicDrinksState by viewModel.nonAlcoholicDrinksState.collectAsState()
    val selectedTabIndex by viewModel.chosenStateIndex.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setChosenStateIndex(AlcoholicDrinksIndex)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            Tab(
                modifier = Modifier.padding(vertical = 4.dp),
                selected = selectedTabIndex == AlcoholicDrinksIndex,
                onClick = {
                    viewModel.setChosenStateIndex(AlcoholicDrinksIndex)
                    viewModel.setCocktailToFind("")
                }
            ) {
                Text("Alcoholic")
            }
            Tab(
                modifier = Modifier.padding(vertical = 4.dp),
                selected = selectedTabIndex == NonAlcoholicDrinksIndex,
                onClick = {
                    viewModel.setChosenStateIndex(NonAlcoholicDrinksIndex)
                    viewModel.setCocktailToFind("")
                }
            ) {
                Text("Non Alcoholic")
            }
        }
        if (isLoading){
            LoadingScreen()
        }
        else if (alcoholicDrinksState.error != null){
            ErrorScreen(alcoholicDrinksState.error!!)
        }
        else if (alcoholicDrinksState.result.isNotEmpty() || nonAlcoholicDrinksState.result.isNotEmpty()){
            if (selectedTabIndex == AlcoholicDrinksIndex){
                SearchTab(viewModel, alcoholicDrinksState.result)
                DrinksGrid(viewModel, alcoholicDrinksState.result, navHostController)
            }
            else if (selectedTabIndex == NonAlcoholicDrinksIndex){
                SearchTab(viewModel, nonAlcoholicDrinksState.result)
                DrinksGrid(viewModel, nonAlcoholicDrinksState.result, navHostController)
            }
        }
    }
}

@Composable
fun SearchTab(viewModel: DrinksViewModel, list: List<DrinkData>) {
    val cocktailToFind by viewModel.cocktailToFind.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth().height(24.dp)
    ) {
        BasicTextField(
            modifier = Modifier.padding(4.dp),
            value = cocktailToFind,
            onValueChange = {
                viewModel.setCocktailToFind(it)
                viewModel.getCocktailsToFind(list)
            }
        )
        HorizontalDivider(
            thickness = 4.dp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun DrinksGrid(viewModel: DrinksViewModel,result: List<DrinkData>, navHostController: NavHostController) {
    val findingResult by viewModel.resultOfFindings.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ){
        if (findingResult.isEmpty()){
            items(result){
                    drink ->
                DrinkCell(drink, onClick = {
                    navHostController.navigate(route = Graph.drinkScreen)
                    viewModel.setDrinkToOpen(drink.idDrink)
                })
            }
        }
        else{
            items(findingResult){
                    drink ->
                DrinkCell(drink, onClick = {
                    navHostController.navigate(route = Graph.drinkScreen)
                    viewModel.setDrinkToOpen(drink.idDrink)
                })
            }
        }
    }
}

@Composable
fun DrinkCell(drink: DrinkData, onClick: () -> Unit) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                scaleIn(animationSpec = tween(durationMillis = 500))
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .padding(16.dp)
                .clickable { onClick() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.height(200.dp),
                model = drink.strDrinkThumb,
                contentDescription = drink.strDrink
            )
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = drink.strDrink,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

