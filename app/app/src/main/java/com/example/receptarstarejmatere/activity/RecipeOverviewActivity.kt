package com.example.receptarstarejmatere.activity

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.IngredientsAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.IngredientWithMeasure
import com.example.receptarstarejmatere.database.model.RecipeWithTags
import com.example.receptarstarejmatere.utils.Constants
import kotlinx.android.synthetic.main.activity_recipe_overview.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipeOverviewActivity : AppCompatActivity() {

    private lateinit var recipeWithTags: RecipeWithTags
    private var ingredientWithMeasure: ArrayList<IngredientWithMeasure> = ArrayList()
    private lateinit var adapter: IngredientsAdapter
    private lateinit var favoritesStar: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_overview)
        val selectedRecipeId = intent.getIntExtra(Constants.SELECTED_RECIPE_ID, 100)
        adapter = IngredientsAdapter()

        val headerText = intent?.getStringExtra(Constants.SELECTED_RECIPE_NAME)
        recipe_overview_header.text = headerText

        initRecipe(selectedRecipeId)
        initIngredientsAdapter(selectedRecipeId)

        favoritesStar = recipe_overview_star
        favoritesStar.setOnClickListener {
            setFavoriteRecipe(favoritesStar.isChecked)
        }
    }

    private fun setFavoriteRecipe(isFavorite: Boolean) {
        GlobalScope.launch {
            App.recipeRepository.updateIsFavorite(recipeWithTags.recipe.id, isFavorite)
        }
    }

    private fun initRecipe(selectedRecipeId: Int) {
        App.recipeTagRepository.getTagsForRecipe(selectedRecipeId)
            .observe(this, Observer { recipeAndTags ->
                recipeWithTags = recipeAndTags
                printRecipeBasic(recipeWithTags)
                favoritesStar.isChecked = recipeWithTags.recipe.isFavorite
            })
    }

    private fun initIngredientsAdapter(selectedRecipeId: Int) {
        App.recipeIngredientRepository.getIngredientsForRecipe(selectedRecipeId)
            .observe(this, Observer { recipeIngredients ->
                ingredientWithMeasure.clear()
                ingredientWithMeasure.addAll(recipeIngredients)

                adapter.swapData(ingredientWithMeasure)

                val recyclerView = ingredients_list
                recyclerView.adapter = adapter
            })
    }

    private fun printRecipeBasic(recipeWithTags: RecipeWithTags?) {
        if (recipeWithTags == null) {
            return
        }

        recipe_tags.text = recipeWithTags.tags.joinToString(separator = ", ") {
            it.name
        }

        printResource()
        printCookingTime()
        printCookingTemperature()
        printPreparationTime()
        printInstructions()
    }

    private fun printResource() {
        if (recipeWithTags.recipe.source.isEmpty()) { // TODO ""
            recipe_source.visibility = TextView.GONE
            recipe_source_label.visibility = TextView.GONE
            return
        }
        recipe_source.text = recipeWithTags.recipe.source
    }

    private fun printCookingTime() {
        if (recipeWithTags.recipe.cookingTime.isEmpty()) {
            recipe_cooking_time.visibility = TextView.GONE
            recipe_cooking_time_label.visibility = TextView.GONE
            recipe_cooking_time_unit_text.visibility = TextView.GONE
            return
        }
        recipe_cooking_time.text = recipeWithTags.recipe.cookingTime
    }

    private fun printCookingTemperature() {
        if (recipeWithTags.recipe.cookingTemperature.isEmpty()) {
            recipe_cooking_temp.visibility = TextView.GONE
            recipe_cooking_temp_label.visibility = TextView.GONE
            recipe_cooking_temp_unit_text.visibility = TextView.GONE
            return
        }
        recipe_cooking_temp.text = recipeWithTags.recipe.cookingTemperature
    }

    private fun printPreparationTime() {
        if (recipeWithTags.recipe.preparationTime.isEmpty()) {
            recipe_prep_time.visibility = TextView.GONE
            recipe_prep_time_label.visibility = TextView.GONE
            recipe_prep_time_unit_text.visibility = TextView.GONE
            return
        }
        recipe_prep_time.text = recipeWithTags.recipe.preparationTime
    }

    private fun printInstructions() {
        if (recipeWithTags.recipe.instructions.isEmpty()) { // TODO ""
            recipe_instructions.visibility = TextView.GONE
            recipe_ingredients_label.visibility = TextView.GONE
            return
        }
        recipe_instructions.text = recipeWithTags.recipe.instructions
    }

}
