package com.example.receptarstarejmatere.activity

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.utils.Constants

class EditRecipeActivity : AppCompatActivity() {

    private lateinit var recipe: Recipe

    private lateinit var favoritesStar: CheckBox
    private lateinit var recipeName: EditText
    private lateinit var sourceEditText: EditText
    private lateinit var prepTimeEditText: EditText
    private lateinit var cookTimeEditText: EditText
    private lateinit var cookTempEditText: EditText
    private lateinit var instructionsEditText: EditText
    private lateinit var ingredNameEditText: EditText
    private lateinit var ingredMeasureEditText: EditText
    private lateinit var ingredQuantityEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        App.recipeRepository.getRecipeById(intent.getIntExtra(Constants.EDITED_RECIPE_ID, 0))
            .observe(this, Observer { recipeFromDb ->
                recipe = recipeFromDb
                initValues()
            })

        val headerText = "Recept na " + intent?.getStringExtra(Constants.EDITED_RECIPE_NAME)
        findViewById<TextView>(R.id.edit_recipe_header).text = headerText

        favoritesStar = findViewById(R.id.edit_recipe_star)
        recipeName = findViewById(R.id.edit_recipe_name)
        sourceEditText = findViewById(R.id.edit_recipe_source)
        prepTimeEditText = findViewById(R.id.edit_recipe_prep_time)
        cookTimeEditText = findViewById(R.id.edit_recipe_cook_time)
        cookTempEditText =  findViewById(R.id.edit_recipe_cook_temp)
        instructionsEditText = findViewById(R.id.edit_recipe_instructions)

        val saveButton = findViewById<Button>(R.id.edit_recipe_save_button)
        saveButton.setOnClickListener {

        }

    }

    private fun initValues() {
        favoritesStar.isChecked = recipe.isFavorite
        recipeName.setText(recipe.name)
        sourceEditText.setText(recipe.source)
        prepTimeEditText.setText(recipe.preparationTime)
        cookTimeEditText.setText(recipe.cookingTime)
        cookTempEditText.setText(recipe.cookingTemperature)
        instructionsEditText.setText(recipe.instructions)
    }
}
