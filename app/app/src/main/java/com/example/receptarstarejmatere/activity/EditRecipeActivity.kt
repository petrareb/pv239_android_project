package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.RecipeTagsAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.database.model.Tag
import com.example.receptarstarejmatere.database.viewModel.TagViewModel
import com.example.receptarstarejmatere.utils.Constants
import com.example.receptarstarejmatere.utils.EditTextUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditRecipeActivity : AppCompatActivity(), RecipeTagsAdapter.OnSelectTagListener {

    private lateinit var recipe: Recipe
    private var tags: MutableList<TagViewModel> = mutableListOf()

    private lateinit var tagsAdapter: RecipeTagsAdapter

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

    private lateinit var tagsLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        tagsAdapter = RecipeTagsAdapter(onTagListener = this)

        initRecipe()
        initTagsRecyclerView()


        tagsLabel = findViewById(R.id.edit_recipe_tags_text)

        val headerText = "Recept na " + intent?.getStringExtra(Constants.EDITED_RECIPE_NAME)
        findViewById<TextView>(R.id.edit_recipe_header).text = headerText

        favoritesStar = findViewById(R.id.edit_recipe_star)
        recipeName = findViewById(R.id.edit_recipe_name)
        sourceEditText = findViewById(R.id.edit_recipe_source)
        prepTimeEditText = findViewById(R.id.edit_recipe_prep_time)
        cookTimeEditText = findViewById(R.id.edit_recipe_cook_time)
        cookTempEditText = findViewById(R.id.edit_recipe_cook_temp)
        val cookTempLabel = findViewById<TextView>(R.id.edit_recipe_cook_temp_text)
        instructionsEditText = findViewById(R.id.edit_recipe_instructions)

        EditTextUtils.showAnotherEditTextIfNotEmpty(
            cookTimeEditText,
            cookTempEditText,
            cookTempLabel
        )

        val saveButton = findViewById<Button>(R.id.edit_recipe_save_button)
        saveButton.setOnClickListener {
            updateRecipeValues()

            GlobalScope.launch {
                App.recipeRepository.updateRecipe(recipe)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRecipe() {
        App.recipeRepository.getRecipeById(intent.getIntExtra(Constants.EDITED_RECIPE_ID, 0))
            .observe(this, Observer { recipeFromDb ->
                recipe = recipeFromDb
                initViewValues()
            })
    }

    private fun initViewValues() {
        favoritesStar.isChecked = recipe.isFavorite
        recipeName.setText(recipe.name)
        sourceEditText.setText(recipe.source)
        prepTimeEditText.setText(recipe.preparationTime)
        cookTimeEditText.setText(recipe.cookingTime)
        cookTempEditText.setText(recipe.cookingTemperature)
        instructionsEditText.setText(recipe.instructions)
    }

    private fun updateRecipeValues() {
        recipe.isFavorite = favoritesStar.isChecked
        recipe.name = recipeName.text.toString()
        recipe.preparationTime = prepTimeEditText.text.toString()
        recipe.source = sourceEditText.text.toString()
        recipe.cookingTime = cookTimeEditText.text.toString()
        recipe.cookingTemperature = cookTempEditText.text.toString()
        recipe.instructions =
            if (instructionsEditText.text.isEmpty()) "" else instructionsEditText.text.toString()
    }

    private fun initTagsRecyclerView() {
        App.tagRepository.getAllTags().observe(this, Observer { tagsFromDb: List<Tag> ->

            tagsFromDb.forEach { tag ->
                val tagModel = TagViewModel(tag)
                tags.add(tagModel)
            }

            tagsAdapter.swapData(tags)

            val recyclerView = findViewById<RecyclerView>(R.id.edit_recipe_tags_list)
            recyclerView.adapter = tagsAdapter
        })
    }

    override fun onSelectTag() {
        tagsLabel.error = null
    }

}
