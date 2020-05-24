package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity(
    tableName = "recipes_ingredients",
    indices = [
        Index(value = ["recipe_id", "ingredient_id"]),
        Index(value = ["id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["recipe_id"],
            childColumns = ["recipe_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["ingredient_id"],
            childColumns = ["ingredient_id"],
            onDelete = ForeignKey.CASCADE
        )]
)
class RecipeIngredient(
    @ColumnInfo(name = "recipe_id") var recipeId: Int, @ColumnInfo(name = "ingredient_id") var ingredientId: Int,// mnozstvo, napr. 5
    @ColumnInfo(name = "quantity") var quantity: Int,// jednotka mnozstva, PL
    @ColumnInfo(name = "measure") var measure: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}

class IngredientWithMeasure(// mnozstvo, napr. 5
    @ColumnInfo(name = "name") var name: String, @ColumnInfo(name = "ingredient_id") var ingredientId: Int, @ColumnInfo(
        name = "quantity"
    ) var quantity: Int,// jednotka mnozstva, PL
    @ColumnInfo(name = "measure") var measure: String
)
