package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.NewRecipeIngredientsAdapter
import com.example.receptarstarejmatere.adapter.RecipeTagsAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.database.model.RecipeTagCrossRef
import com.example.receptarstarejmatere.database.model.Tag
import com.example.receptarstarejmatere.database.viewModel.IngredientViewModel
import com.example.receptarstarejmatere.database.viewModel.TagViewModel
import com.example.receptarstarejmatere.utils.EditTextUtils
import com.example.receptarstarejmatere.utils.SaveIngredientsUtils
import kotlinx.android.synthetic.main.activity_new_recipe.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewRecipeActivity : AppCompatActivity(), RecipeTagsAdapter.OnSelectTagListener {

    private lateinit var ingredientsAdapter: NewRecipeIngredientsAdapter
    private lateinit var tagsAdapter: RecipeTagsAdapter

    private val ingredients: MutableList<IngredientViewModel> = mutableListOf()
    private var tags: MutableList<TagViewModel> = mutableListOf()

    private lateinit var isFavoriteStar: CheckBox
    private lateinit var nameEditText: EditText
    private lateinit var sourceEditText: EditText
    private lateinit var prepTimeEditText: EditText
    private lateinit var cookTimeEditText: EditText
    private lateinit var instructionsEditText: EditText
    private lateinit var ingredNameEditText: EditText
    private lateinit var ingredMeasureEditText: EditText
    private lateinit var ingredQuantityEditText: EditText

    private lateinit var cookTempEditText: EditText
    private lateinit var cookTempLabel: TextView
    private lateinit var cookTempUnits: TextView

    private lateinit var tagsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe)

        ingredientsAdapter = NewRecipeIngredientsAdapter()
        initIngredientsRecyclerView()

        tagsAdapter = RecipeTagsAdapter(onTagListener = this)
        initTagsRecyclerView()

        initViewComponents()

        val saveButton = new_recipe_save_button
        saveButton.setOnClickListener {
            val isValid = checkRequiredFields()
            if (isValid) {
                saveNewRecipe()
                Toast.makeText(
                    this,
                    resources.getString(R.string.success_new_recipe),
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        val addIngredButton = new_recipe_add_ingred_button
        addIngredButton.setOnClickListener {
            val isValid = checkNewIngredientValues()
            if (isValid) {
                val newIngred =
                    IngredientViewModel(
                        name = ingredNameEditText.text.toString(),
                        measure = ingredMeasureEditText.text.toString(),
                        quantity = ingredQuantityEditText.text.toString()
                    )
                ingredients.add(newIngred)
                ingredientsAdapter.swapData(ingredients)

                ingredMeasureEditText.text.clear()
                ingredQuantityEditText.text.clear()
                ingredNameEditText.text.clear()
            }
        }
    }

    private fun initViewComponents() {
        isFavoriteStar = new_recipe_star
        nameEditText = new_recipe_name
        sourceEditText = new_recipe_source
        prepTimeEditText = new_recipe_prep_time
        cookTimeEditText = new_recipe_cook_time
        instructionsEditText = new_recipe_instructions

        cookTempEditText = new_recipe_cook_temp
        cookTempLabel = new_recipe_cook_temp_text
        cookTempUnits = new_recipe_cook_temp_unit_text

        ingredNameEditText = new_recipe_ingred_name
        ingredMeasureEditText = new_recipe_ingred_measure
        ingredQuantityEditText = new_recipe_ingred_quantity

        tagsText = new_recipe_tags_text

        cookTempEditText.visibility = View.GONE
        cookTempLabel.visibility = View.GONE
        cookTempUnits.visibility = View.GONE

        EditTextUtils.showAnotherEditTextIfNotEmpty(
            cookTimeEditText,
            listOf(cookTempEditText, cookTempLabel, cookTempUnits)
        )
    }

    private fun saveNewRecipe() {
        val isFavorite = isFavoriteStar.isChecked
        val name = nameEditText.text.toString()
        val prepTime = prepTimeEditText.text.toString()
        val source = sourceEditText.text.toString()
        val cookTime = cookTimeEditText.text.toString()
        val cookTemp = cookTempEditText.text.toString()
        val instructions =
            if (instructionsEditText.text.isEmpty()) "" else instructionsEditText.text.toString()

        val newRecipe = Recipe(
            name = name,
            cookingTemperature = cookTemp,
            cookingTime = cookTime,
            instructions = instructions,
            isFavorite = isFavorite,
            preparationTime = prepTime,
            source = source
        )

        GlobalScope.launch {
            val id = App.recipeRepository.insert(newRecipe).toInt()
            saveIngredients(id)
            saveTags(id)
        }
    }

    private suspend fun saveTags(recipeId: Int) {
        val tagsToSave = tags
            .filter { tagModel ->
                tagModel.isSelected
            }
            .map { tagModel ->
                RecipeTagCrossRef(
                    recipeId = recipeId,
                    tagId = tagModel.tagId
                )
            }
        App.recipeTagRepository.insertAll(tagsToSave)
    }


    private suspend fun saveIngredients(recipeId: Int) {
        ingredients.forEach { ingred ->
            val ingredientId = SaveIngredientsUtils.getOrCreateIngredientId(ingred)
            SaveIngredientsUtils.joinIngredientsToRecipe(recipeId, ingredientId, ingred)
        }
    }

    private fun checkRequiredFields(): Boolean {
        var isValid = true
        if (nameEditText.text.isEmpty()) {
            nameEditText.error = getString(R.string.new_recipe_error_name)
            isValid = false
        }
        if (ingredients.size == 0) {
            ingredNameEditText.error = getString(R.string.new_recipe_error_0_ing)
            isValid = false
        }
        if (!checkTags()) {
            isValid = false
        }

        return isValid
    }

    private fun checkTags(): Boolean {
        return if (!tags.any { tag -> tag.isSelected }) {
            tagsText.error = getString(R.string.new_recipe_error_0_tags)
            false
        } else {
            tagsText.error = null
            true
        }
    }

    private fun checkNewIngredientValues(): Boolean {
        var isValid = true
        if (ingredMeasureEditText.text.isEmpty()) {
            ingredMeasureEditText.error = getString(R.string.new_recipe_error_ing_measure)
            isValid = false
        }
        if (ingredNameEditText.text.isEmpty()) {
            ingredNameEditText.error = getString(R.string.new_recipe_error_ing_name)
            isValid = false
        }
        if (ingredQuantityEditText.text.isEmpty()) {
            ingredQuantityEditText.error = getString(R.string.new_recipe_error_ing_quantity)
            isValid = false
        }
        return isValid
    }

    private fun initIngredientsRecyclerView() {
        // initialized with empty list
        val recyclerView = findViewById<RecyclerView>(R.id.new_recipe_ingred_list)
        recyclerView.adapter = ingredientsAdapter
    }

    private fun initTagsRecyclerView() {
        App.tagRepository.getAllTags().observe(this, Observer { tagsFromDb: List<Tag> ->

            tagsFromDb.forEach { tag ->
                tags.add(TagViewModel(tag.tagId, tag.name))
            }

            tagsAdapter.swapData(tags)

            val recyclerView = findViewById<RecyclerView>(R.id.new_recipe_tags_list)
            recyclerView.adapter = tagsAdapter
        })
    }

    override fun onSelectTag() {
        tagsText.error = null
    }
}
