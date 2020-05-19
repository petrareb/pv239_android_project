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
import com.example.receptarstarejmatere.adapter.EditIngredientsAdapter
import com.example.receptarstarejmatere.adapter.RecipeTagsAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.AllTagsWithRecipes
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.database.model.RecipeTagCrossRef
import com.example.receptarstarejmatere.database.model.Tag
import com.example.receptarstarejmatere.database.viewModel.IngredientViewModel
import com.example.receptarstarejmatere.database.viewModel.TagViewModel
import com.example.receptarstarejmatere.utils.Constants
import com.example.receptarstarejmatere.utils.EditTextUtils
import com.example.receptarstarejmatere.utils.SaveIngredientsUtils
import kotlinx.android.synthetic.main.ingr_measure_list_item.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditRecipeActivity : AppCompatActivity(), RecipeTagsAdapter.OnSelectTagListener {

    private lateinit var recipe: Recipe
    private var tags: MutableList<TagViewModel> = mutableListOf()
    private var ingredients: MutableList<IngredientViewModel> = mutableListOf()

    private lateinit var tagsAdapter: RecipeTagsAdapter
    private lateinit var ingredientsAdapter: EditIngredientsAdapter

    private lateinit var favoritesStar: CheckBox
    private lateinit var recipeName: EditText
    private lateinit var sourceEditText: EditText
    private lateinit var prepTimeEditText: EditText
    private lateinit var cookTimeEditText: EditText
    private lateinit var cookTempEditText: EditText
    private lateinit var instructionsEditText: EditText
    private lateinit var newIngredNameEditText: EditText
    private lateinit var newIngredMeasureEditText: EditText
    private lateinit var newIngredQuantityEditText: EditText

    private lateinit var tagsLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        tagsAdapter = RecipeTagsAdapter(onTagListener = this)


        ingredientsAdapter = EditIngredientsAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.edit_recipe_ingred_list)
        recyclerView.adapter = ingredientsAdapter

        initRecipe()
        initTagsRecyclerView()
        initIngredients()


        tagsLabel = findViewById(R.id.edit_recipe_tags_text)

        val headerText = intent?.getStringExtra(Constants.EDITED_RECIPE_NAME)
        findViewById<TextView>(R.id.edit_recipe_header).text = headerText

        favoritesStar = findViewById(R.id.edit_recipe_star)
        recipeName = findViewById(R.id.edit_recipe_name)
        sourceEditText = findViewById(R.id.edit_recipe_source)
        prepTimeEditText = findViewById(R.id.edit_recipe_prep_time)
        cookTimeEditText = findViewById(R.id.edit_recipe_cook_time)
        cookTempEditText = findViewById(R.id.edit_recipe_cook_temp)
        val cookTempLabel = findViewById<TextView>(R.id.edit_recipe_cook_temp_text)
        val cookTempUnits = findViewById<TextView>(R.id.edit_recipe_cook_temp_unit_text)
        instructionsEditText = findViewById(R.id.edit_recipe_instructions)

        newIngredMeasureEditText = findViewById(R.id.edit_recipe_ingred_measure)
        newIngredNameEditText = findViewById(R.id.edit_recipe_ingred_name)
        newIngredQuantityEditText = findViewById(R.id.edit_recipe_ingred_quantity)

        EditTextUtils.showAnotherEditTextIfNotEmpty(
            cookTimeEditText, listOf(cookTempEditText, cookTempLabel, cookTempUnits)
        )

        val addIngredButton = findViewById<Button>(R.id.edit_recipe_add_ingred_button)
        addIngredButton.setOnClickListener {
            val newIngredient = createNewIngredient()
            ingredients.add(newIngredient)
            ingredientsAdapter.swapData(ingredients)

            newIngredMeasureEditText.text.clear()
            newIngredNameEditText.text.clear()
            newIngredQuantityEditText.text.clear()
        }

        val saveButton = findViewById<Button>(R.id.edit_recipe_save_button)
        saveButton.setOnClickListener {
            updateRecipeValues()

            GlobalScope.launch {
                App.recipeRepository.updateRecipe(recipe)
                App.recipeIngredientRepository.deleteIngredientsByRecipeId(recipe.id)
                App.recipeTagRepository.deleteCrossRefByRecipeId(recipe.id) // delete cross ref
                saveIngredients(recipe.id)
                saveTags(recipe.id)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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

    private fun createNewIngredient(): IngredientViewModel {
        return IngredientViewModel(
            name = newIngredNameEditText.text.toString(),
            quantity = newIngredQuantityEditText.text.toString(),
            measure = newIngredMeasureEditText.text.toString()
        )
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
        App.tagRepository.getAllTagsWithRecipes(
            intent.getIntExtra(
                Constants.EDITED_RECIPE_ID,
                0
            )
        ).observe(this, Observer { tagsFromDb: List<AllTagsWithRecipes> ->

            tagsFromDb.forEach { tag ->
                var tagModel : TagViewModel = TagViewModel(tag.tagId, tag.tagName)
                if (tag.recipeId != 0 ) { // default value
                    tagModel.isSelected = true
                }
                tags.add(tagModel)
            }
            tagsAdapter.swapData(tags)
            val recyclerView = findViewById<RecyclerView>(R.id.edit_recipe_tags_list)
            recyclerView.adapter = tagsAdapter

        })
    }

    private fun initIngredients() {
        App.recipeIngredientRepository.getIngredientsForRecipe(
            intent.getIntExtra(
                Constants.EDITED_RECIPE_ID,
                0
            )
        )
            .observe(this, Observer { recipeWithIngreds ->
                ingredients.addAll(recipeWithIngreds.map { ingred ->
                    IngredientViewModel(
                        name = ingred.name,
                        measure = ingred.measure,
                        quantity = ingred.quantity.toString()
                    )
                })
                ingredientsAdapter.swapData(ingredients)
            })
    }

    override fun onSelectTag() {
        tagsLabel.error = null
    }
}
