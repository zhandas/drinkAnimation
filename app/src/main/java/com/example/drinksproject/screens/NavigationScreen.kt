package com.example.drinksproject.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drinksproject.viewmodel.DrinksViewModel

@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()
    val viewModel: DrinksViewModel = viewModel()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Graph.mainScreen
        ){
        composable(route = Graph.mainScreen){
            MainScreen(viewModel, navController)
        }
        composable(route = Graph.drinkScreen){
            DrinkDetail(viewModel)
        }
    }
}

object Graph{
    val mainScreen = "MainScreen"
    val drinkScreen = "DrinkScreen"
}