package com.example.receptarstarejmatere.activity

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.IngredientsAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.IngredientWithMeasure
import com.example.receptarstarejmatere.database.model.RecipeWithTags
import com.example.receptarstarejmatere.utils.Constants
import kotlinx.android.synthetic.main.activity_recipe_overview.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipeOverviewActivity: AppCompatActivity() {

    private lateinit var recipeWithTags : RecipeWithTags
    private var ingredientWithMeasure : ArrayList<IngredientWithMeasure> = ArrayList()
    private lateinit var adapter: IngredientsAdapter
    private lateinit var favoritesStar: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_overview)
        val selectedRecipeId = intent.getIntExtra(Constants.SELECTED_RECIPE_ID, 100)
        adapter = IngredientsAdapter()

        val headerText = intent?.getStringExtra(Constants.SELECTED_RECIPE_NAME)
        findViewById<TextView>(R.id.recipe_overview_header).text = headerText

        favoritesStar = findViewById(R.id.recipe_overview_star)

        initRecipe(selectedRecipeId)
        initIngredientsAdapter(selectedRecipeId)

        favoritesStar.setOnClickListener{
            setFavoriteRecipe(favoritesStar.isChecked)
        }
    }

    private fun setFavoriteRecipe(isFavorite: Boolean) {
        GlobalScope.launch {
            App.recipeRepository.updateIsFavorite(recipeWithTags.recipe.id, isFavorite)
        }
    }

    private fun initRecipe(selectedRecipeId : Int) {
        App.recipeTagRepository.getTagsForRecipe(selectedRecipeId).observe(this, Observer { recipeAndTags ->
            recipeWithTags = recipeAndTags
            printRecipeBasic(recipeWithTags)
            favoritesStar.isChecked = recipeWithTags.recipe.isFavorite
        })
    }

    private fun initIngredientsAdapter(selectedRecipeId: Int) {
        App.recipeIngredientRepository.getIngredientsForRecipe(selectedRecipeId).observe(this, Observer { recipeIngredients ->
            ingredientWithMeasure.clear()
            ingredientWithMeasure.addAll(recipeIngredients)

            adapter.swapData(ingredientWithMeasure)

            val recyclerView = findViewById<RecyclerView>(R.id.ingredients_list)
            recyclerView.adapter = adapter
        })
    }

    private fun printRecipeBasic(recipeWithTags : RecipeWithTags?){
        if (recipeWithTags == null) {
            return
        }

        recipe_tags.text = recipeWithTags.tags.joinToString(separator = ", ") {
            it.name }

        if (recipeWithTags.recipe.source == ""){
            recipe_source.visibility = TextView.GONE
            recipe_source_label.visibility = TextView.GONE
        }
        else {
            recipe_source.text = recipeWithTags.recipe.source
        }
        if (recipeWithTags.recipe.cookingTemperature.isEmpty()) {
            recipe_cooking_temp.visibility = TextView.GONE
            recipe_cooking_temp_label.visibility = TextView.GONE
        }
        else {
            recipe_cooking_temp.text = recipeWithTags.recipe.cookingTemperature
        }
        if (recipeWithTags.recipe.cookingTime.isEmpty()) {
            recipe_cooking_time.visibility = TextView.GONE
            recipe_cooking_time_label.visibility = TextView.GONE
        }
        else {
            recipe_cooking_time.text = recipeWithTags.recipe.cookingTime
        }
        if (recipeWithTags.recipe.preparationTime.isEmpty()) {
            recipe_prep_time.visibility = TextView.GONE
            recipe_prep_time_label.visibility = TextView.GONE
        }
        else {
            recipe_prep_time.text = recipeWithTags.recipe.preparationTime
        }
        if (recipeWithTags.recipe.instructions == "") {
            recipe_instructions.visibility = TextView.GONE
            recipe_ingredients_label.visibility = TextView.GONE
        }
        else {
            recipe_instructions.text = recipeWithTags.recipe.instructions
        }
    }
}
