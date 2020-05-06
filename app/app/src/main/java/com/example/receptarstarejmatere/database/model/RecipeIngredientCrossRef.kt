package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity(primaryKeys = ["recipe_id", "ingredient_id"])
data class RecipeIngredientCrossRef (
    @ColumnInfo(name= "recipe_id", index = true) val recipeId : Int,
    @ColumnInfo(name = "tag_id", index = true) val ingredientId : Int,
    val quantity : Int, // mnozstvo, napr. 5
    val measure : String // jednotka mnozstva, PL
)

data class RecipeWithIngredients (
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "rowid",
        entityColumn = "rowid",

        associateBy = Junction(
            value = RecipeIngredientCrossRef::class,
            parentColumn = "ingredient_id",
            entityColumn = "tag_id"
        )
    )
    val tags : List<Ingredient>
)

data class IngredientWithRecipes (
    @Embedded val ingredient : Ingredient,
    @Relation(
        parentColumn = "rowid",
        entityColumn = "rowid",
        associateBy = Junction(
            value = RecipeIngredientCrossRef::class,
            parentColumn = "ingredient_id",
            entityColumn = "recipe_id"
        )
    )
    val recipes : List<Recipe>
)