package com.example.receptarstarejmatere.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.receptarstarejmatere.database.model.Recipe
import java.lang.IllegalArgumentException
import java.util.*

class RecipeViewModel(): ViewModel() {
    val recipeId: String = "0"
    val recipe: LiveData<Recipe> = TODO()
}

//class RecipeViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
//    val recipeId: String = savedStateHandle["recipeId"] ?:
//            throw IllegalArgumentException("Recipe id is missing in SavedStateHandle.")
//    val recipe: LiveData<Recipe> = TODO()
//}