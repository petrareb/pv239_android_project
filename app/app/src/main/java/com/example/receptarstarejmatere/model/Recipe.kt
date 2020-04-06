package com.example.receptarstarejmatere.model

data class Recipe(
    val name: String,
    val isFavorite: Boolean,
    val preparationTime: String,
    val cookingTime: String,
    val instructions: String
    // * number of stars
    // tags
    // ingredients
    // route ti an image in local storage
// TODO: make @seralizedName for attributes
)
