package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.RecipesAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.utils.Constants
import com.example.receptarstarejmatere.utils.SwipeRecipeCallback

class RecipesActivity : AppCompatActivity(), RecipesAdapter.OnRecipeListener {

    private var mRecipes: ArrayList<Recipe> = ArrayList()
    private lateinit var adapter: RecipesAdapter
    private var isFavorites : Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        adapter = RecipesAdapter(onRecipeListener = this)
        var header : String? = ""
        isFavorites = intent?.getBooleanExtra(Constants.FAVORITE_RECIPES, false)
        if (isFavorites == true) {
            header = getString(R.string.favorites)
            initFavoritesRecyclerView()
            findViewById<TextView>(R.id.no_recipes_found).text = getString(R.string.no_favorite_recipes_found)
        }
        if (isFavorites == false) {
            header = intent?.getStringExtra(Constants.SELECTED_TAG_NAME)
            initRecipesRecyclerView()
        }
        findViewById<TextView>(R.id.recipes_header).text = header
    }

    private fun initRecipesRecyclerView() {
        val selectedTagId = intent.getIntExtra(Constants.SELECTED_TAG_ID, 100)
        App.recipeTagRepository.getTagWithRecipes(selectedTagId).observe(this, Observer { tagsWithRecipes ->
            val recipes = tagsWithRecipes[0].recipes
            initRecipesRecyclerShared(recipes)
        })
    }

    private fun initFavoritesRecyclerView() {
        App.recipeRepository.getFavoriteRecipes().observe(this, Observer { favorites ->
            initRecipesRecyclerShared(favorites)
        })
    }

    private fun initRecipesRecyclerShared(recipes : List<Recipe>){
        mRecipes.clear()
        mRecipes.addAll(recipes)

        adapter.swapData(mRecipes)

        val recyclerView = findViewById<RecyclerView>(R.id.recipes_list)
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(SwipeRecipeCallback(adapter, this))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        if (mRecipes.isEmpty()) {
            findViewById<TextView>(R.id.no_recipes_found).visibility = View.VISIBLE
        } else {
            findViewById<TextView>(R.id.no_recipes_found).visibility = View.GONE
        }
    }

    override fun onSelectedRecipeClick(position: Int) {
        val intent = Intent(this, RecipeOverviewActivity::class.java)
        intent.putExtra(Constants.SELECTED_RECIPE_ID, mRecipes[position].id)
        intent.putExtra(Constants.SELECTED_RECIPE_NAME, mRecipes[position].name)
        startActivity(intent)
    }

    override fun onSelectedRecipeEditSwipe(position: Int) {
        val intent = Intent(this, EditRecipeActivity::class.java)
        intent.putExtra(Constants.EDITED_RECIPE_ID, mRecipes[position].id)
        intent.putExtra(Constants.EDITED_RECIPE_NAME, mRecipes[position].name)
        startActivity(intent)
    }
}
