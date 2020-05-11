package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity(
    tableName = "recipes_ingredients",
    indices = [
        Index(value = ["recipe_id", "ingredient_id"])
    ],
    foreignKeys = [
    ForeignKey(entity = Recipe::class, parentColumns = ["recipe_id"], childColumns = ["recipe_id"]),
    ForeignKey(entity = Ingredient::class, parentColumns = ["ingredient_id"], childColumns = ["ingredient_id"])]
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




//@Entity(primaryKeys = ["recipe_id", "ingredient_id"])
//data class RecipeIngredientCrossRef (
//    @ColumnInfo(name= "recipe_id", index = true) val recipeId : Int,
//    @ColumnInfo(name = "ingredient_id", index = true) val ingredientId : Int,
//    val quantity : Int, // mnozstvo, napr. 5
//    val measure : String // jednotka mnozstva, PL
//)

//data class  RecipeIdWithIngredients(
//    //val recipeId: Int,
//    val ingredientsWithMeasures : List<RecipeIngredientCrossRef>
//)

//data class  IngredientsWithRecipes(
//    val ingredientId: Int,
//    val recipesWithMeasure : List<RecipeIngredientCrossRef>
//)

//data class RecipeWithIngredients (
//    @Embedded val recipe: Recipe,
//    @Relation(
//        parentColumn = "rowid",
//        entityColumn = "rowid",
//
//        associateBy = Junction(
//            value = RecipeIngredientCrossRef::class,
//            parentColumn = "recipe_id",
//            entityColumn = "ingredient_id"
//        )
//    )
//    val ingredients : List<Ingredient>
//    // TODO include quantity and measure
//)

//data class IngredientWithRecipes (
//    @Embedded val ingredient : Ingredient,
//    @Relation(
//        parentColumn = "rowid",
//        entityColumn = "rowid",
//        associateBy = Junction(
//            value = RecipeIngredientCrossRef::class,
//            parentColumn = "ingredient_id",
//            entityColumn = "recipe_id"
//        )
//    )
//    val recipes : List<Recipe>
//    // TODO include quantity and measure
//)