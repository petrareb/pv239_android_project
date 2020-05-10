package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.NewRecipeIngredientsAdapter
import com.example.receptarstarejmatere.adapter.NewRecipeTagsAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.*
import com.example.receptarstarejmatere.database.viewModel.IngredientViewModel
import com.example.receptarstarejmatere.database.viewModel.TagViewModel
import java.util.*
import kotlin.concurrent.thread
import kotlin.random.Random

class NewRecipeActivity : AppCompatActivity() {

    private lateinit var ingredientsAdapter: NewRecipeIngredientsAdapter
    private lateinit var tagsAdapter: NewRecipeTagsAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe)

        ingredientsAdapter = NewRecipeIngredientsAdapter()
        initIngredientsRecyclerView()

        tagsAdapter = NewRecipeTagsAdapter()
        initTagsRecyclerView()

        val saveButton = findViewById<Button>(R.id.new_recipe_save_button)
        val addIngredButton = findViewById<Button>(R.id.new_recipe_add_ingred_button)

        isFavoriteStar = findViewById(R.id.new_recipe_star)
        nameEditText = findViewById(R.id.new_recipe_name)
        sourceEditText = findViewById(R.id.new_recipe_source)
        prepTimeEditText = findViewById(R.id.new_recipe_prep_time)
        cookTimeEditText = findViewById(R.id.new_recipe_cook_time)
        instructionsEditText = findViewById(R.id.new_recipe_instructions)

        cookTempEditText = findViewById(R.id.new_recipe_cook_temp)
        cookTempLabel = findViewById(R.id.new_recipe_cook_temp_text)

        ingredNameEditText = findViewById(R.id.new_recipe_ingred_name)
        ingredMeasureEditText = findViewById(R.id.new_recipe_ingred_measure)
        ingredQuantityEditText = findViewById(R.id.new_recipe_ingred_quantity)

        cookTempEditText.visibility = View.GONE
        cookTempLabel.visibility = View.GONE

        cookTimeEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 0 || s == "0") {
                    cookTempEditText.visibility = View.GONE
                    cookTempLabel.visibility = View.GONE
                } else {
                    cookTempEditText.visibility = View.VISIBLE
                    cookTempLabel.visibility = View.VISIBLE
                }
            }
        })

        saveButton.setOnClickListener {
            val isValid = checkRequiredFields()
            if (isValid) {
                saveNewRecipe()
                Toast.makeText(this, "Nový recept pridaný", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

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

    private fun saveNewRecipe() {
        val isFavorite = isFavoriteStar.isChecked
        val name = nameEditText.text.toString()
        val prepTime = prepTimeEditText.text.toString()
        val source = sourceEditText.text.toString()
        val cookTime = cookTimeEditText.text.toString()
        val cookTemp = cookTempEditText.text.toString()
        val instructions = if (instructionsEditText.text.isEmpty()) "" else instructionsEditText.text.toString()

        val newRecipeId = Random.nextInt(0, 1000000)
        val newRecipe = Recipe(
            name = name,
            cookingTemperature = 180,//cookTemp,
            cookingTime = 30, //cookTime,
            instructions = instructions,
            isFavorite = isFavorite,
            preparationTime = 60, //prepTime,
            source = source,
            pathToImage = "",
            id = newRecipeId
        )

        saveIngredients(newRecipeId)
        saveTags(newRecipeId)

        thread {
            App.recipeRepository.insert(newRecipe)
        }
    }

    private fun saveTags(recipeId: Int) {
        val tagsToSave = tags
            .filter { tagModel ->
                tagModel.isSelected
            }
            .map { tagModel ->
                RecipeTagCrossRef(
                    recipeId = recipeId,
                    tagId = tagModel.tag.tagId
                )
            }

        thread {
            App.recipeTagRepository.insertAll(tagsToSave)
        }
    }


    private fun saveIngredients(recipeId: Int) {
        ingredients.forEach { ingred ->
            val ingredientId = getOrCreateIngredientId(ingred)

            joinIngredientsToRecipe(recipeId, ingredientId, ingred)
        }
    }

    private fun joinIngredientsToRecipe(
        recipeId: Int,
        ingredientId: Int,
        ingred: IngredientViewModel
    ) {
        val newRecipeWithIngredients = RecipeIngredient(
            id = Random.nextInt(0, 1000000),
            ingredientId = ingredientId,
            recipeId = recipeId,
            measure = ingred.measure,
            quantity = 42 //ingred.quantity TODO Int => String
        )
        thread {
            App.recipeIngredientRepository.insert(newRecipeWithIngredients)
        }
    }

    private fun getOrCreateIngredientId(ingred: IngredientViewModel): Int {
        val ingredName = ingred.name.toLowerCase(Locale.ROOT).trim()
        var existingIngredients = listOf<Ingredient>()
        thread {
            existingIngredients = App.ingredientRepository.getByName(ingredName)
        }

        if (existingIngredients.isNotEmpty()) {
            return existingIngredients.first().id
        }

        val newIngredientId = Random.nextInt(0, 1000000)
        val newIngredient = Ingredient(
            id = newIngredientId,
            name = ingredName
        )

        thread {
            App.ingredientRepository.insert(newIngredient)
        }

        return newIngredientId
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
        return isValid
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
                tags.add(TagViewModel(tag))
            }

            tagsAdapter.swapData(tags)

            val recyclerView = findViewById<RecyclerView>(R.id.new_recipe_tags_list)
            recyclerView.adapter = tagsAdapter
        })
    }
}
