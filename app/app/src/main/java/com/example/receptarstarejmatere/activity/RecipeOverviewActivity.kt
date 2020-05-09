package com.example.receptarstarejmatere.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.RecipeWithTags
import com.example.receptarstarejmatere.utils.Constants
import kotlinx.android.synthetic.main.activity_recipe_overview.*
import org.w3c.dom.Text

class RecipeOverviewActivity: AppCompatActivity() {

    private lateinit var recipeWithTags : RecipeWithTags

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_overview)
        val selectedRecipeId = intent.getIntExtra(Constants.SELECTED_RECIPE_ID, 100)

        App.recipeTagRepository.getTagsForRecipe(selectedRecipeId).observe(this, Observer { recipeAndTags ->
                recipeWithTags = recipeAndTags[0]
                printRecipe(recipeWithTags)
            })
    }

    private fun printRecipe(recipeWithTags : RecipeWithTags?){
        if (recipeWithTags == null) {
            return
        }
        recipe_name.text = recipeWithTags.recipe.name
        recipe_tags.text = recipeWithTags.tags.joinToString(separator = ",") {
            it.name }

        if (recipeWithTags.recipe.source == ""){
            recipe_source.visibility = TextView.INVISIBLE
            recipe_source_label.visibility = TextView.INVISIBLE
        }
        else {
            recipe_source.text = recipeWithTags.recipe.source
        }
        if (recipeWithTags.recipe.cookingTemperature == 0) {
            recipe_cooking_temp.visibility = TextView.INVISIBLE
            recipe_cooking_temp_label.visibility = TextView.INVISIBLE
        }
        else {
            recipe_cooking_temp.text = recipeWithTags.recipe.cookingTemperature.toString()
        }
        if (recipeWithTags.recipe.cookingTime == 0) {
            recipe_cooking_time.visibility = TextView.INVISIBLE
            recipe_cooking_time_label.visibility = TextView.INVISIBLE
        }
        else {
            recipe_cooking_time.text = recipeWithTags.recipe.cookingTime.toString()
        }
        if (recipeWithTags.recipe.preparationTime == 0) {
            recipe_prep_time.visibility = TextView.INVISIBLE
            recipe_prep_time_label.visibility = TextView.INVISIBLE
        }
        else {
            recipe_prep_time.text = recipeWithTags.recipe.preparationTime.toString()
        }
        if (recipeWithTags.recipe.instructions == "") {
            recipe_instructions.visibility = TextView.INVISIBLE
            recipe_ingredients_label.visibility = TextView.INVISIBLE
        }
        else {
            recipe_instructions.text = recipeWithTags.recipe.instructions
        }
    }
}
