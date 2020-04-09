package com.example.receptarstarejmatere.database.model

import androidx.room.*

@Entity(primaryKeys = ["recipe_id", "tag_id"])
data class RecipeTagCrossRef (
    @ColumnInfo(name= "recipe_id") val recipeId : Int,
    @ColumnInfo(name = "tag_id") val tagId : Int
)

data class RecipesWithTags (
    @Embedded val recipeId: Recipe,
    @Relation(
        parentColumn = "recipe_id", // mozno to ma byt nieco ine ?
        entityColumn = "tag_id",
        associateBy = Junction(value = RecipeTagCrossRef::class)
        )
    val tags : List<Tag>
)

data class TagsWithRecipes (
    @Embedded val tag : Tag,
    @Relation (
        parentColumn = "tag_id",
        entityColumn = "recipe_id", // mozno tu ma byt nieco ine??
        associateBy = Junction(value = RecipeTagCrossRef::class)
    )
    val recipes : List<Recipe>


)