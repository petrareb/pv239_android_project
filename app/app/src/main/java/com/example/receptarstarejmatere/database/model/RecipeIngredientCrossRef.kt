package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity(
    tableName = "recipes_ingredients",
    indices = [
        Index(value = ["recipe_id", "ingredient_id"]),
        Index(value = ["id"])
    ],
    foreignKeys = [
    ForeignKey(entity = Recipe::class, parentColumns = ["recipe_id"], childColumns = ["recipe_id"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Ingredient::class, parentColumns = ["ingredient_id"], childColumns = ["ingredient_id"],  onDelete = ForeignKey.CASCADE)]
)
class RecipeIngredient {
    constructor(recipeId: Int, ingredientId: Int, quantity: Int, measure: String) {
        this.recipeId = recipeId
        this.ingredientId = ingredientId
        this.quantity = quantity
        this.measure = measure
    }
//    constructor(id : Int, ingredientId: Int, quantity: Int, measure: String, recipeId: Int) {
//        this.id = id
//        this.quantity = quantity
//        this.ingredientId = ingredientId
//        this.measure = measure
//        this.recipeId = recipeId
//    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "recipe_id")
    var recipeId: Int = 0

    @ColumnInfo(name = "ingredient_id")
    var ingredientId: Int = 0

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0 // mnozstvo, napr. 5

    @ColumnInfo(name = "measure")
    var measure: String = "" // jednotka mnozstva, PL
}

class IngredientWithMeasure {
    constructor(name: String, ingredientId: Int, quantity: Int, measure: String) {
        this.name = name
        this.ingredientId = ingredientId
        this.quantity = quantity
        this.measure = measure
    }

    @ColumnInfo(name = "quantity")
    var quantity: Int = 0

    @ColumnInfo(name = "ingredient_id")
    var ingredientId: Int = 0

    @ColumnInfo(name = "name")
    var name: String = "" // mnozstvo, napr. 5

    @ColumnInfo(name = "measure")
    var measure: String = "" // jednotka mnozstva, PL
}
