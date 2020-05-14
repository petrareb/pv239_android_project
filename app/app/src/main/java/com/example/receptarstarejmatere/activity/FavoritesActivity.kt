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
import com.example.receptarstarejmatere.adapter.FavoritesAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.database.repository.RecipeRepository
import com.example.receptarstarejmatere.utils.Constants
import com.example.receptarstarejmatere.utils.SwipeFavoriteRecipeCallback

class FavoritesActivity : AppCompatActivity(), FavoritesAdapter.OnFavoriteRecipeListener {

    private var mFavorites: ArrayList<Recipe> = ArrayList()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        adapter = FavoritesAdapter(onFavoriteListener = this)
        initFavoritesRecyclerView(App.recipeRepository)
    }

    private fun initFavoritesRecyclerView(repo: RecipeRepository) {
        repo.getFavoriteRecipes().observe(this, Observer { recipes ->
            mFavorites.clear()
            mFavorites.addAll(recipes)

            adapter.swapData(recipes.toMutableList())

            val recyclerView = findViewById<RecyclerView>(R.id.favorites_list)
            recyclerView.adapter = adapter

            val itemTouchHelper = ItemTouchHelper(SwipeFavoriteRecipeCallback(adapter, this))
            itemTouchHelper.attachToRecyclerView(recyclerView)

            if (recipes.isEmpty()) {
                findViewById<TextView>(R.id.no_favorites_found).visibility = View.VISIBLE
            } else {
                findViewById<TextView>(R.id.no_favorites_found).visibility = View.GONE
            }
        })
    }

    override fun onSelectedFavoriteRecipeClick(position: Int) {
        val intent = Intent(this, RecipeOverviewActivity::class.java)
        intent.putExtra(Constants.SELECTED_RECIPE_ID, mFavorites[position].id)
        intent.putExtra(Constants.SELECTED_RECIPE_NAME, mFavorites[position].name)
        startActivity(intent)
    }

    override fun onSelectedRecipeEditSwipe(position: Int) {
        val intent = Intent(this, EditRecipeActivity::class.java)
        intent.putExtra(Constants.EDITED_RECIPE_ID, mFavorites[position].id)
        intent.putExtra(Constants.EDITED_RECIPE_NAME, mFavorites[position].name)
        startActivity(intent)
    }
}
