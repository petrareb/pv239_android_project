package com.example.receptarstarejmatere.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.FavoritesAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.repository.RecipeRepository

class FavoritesActivity : AppCompatActivity() {

    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        adapter = FavoritesAdapter()
        initFavoritesRecyclerView(App.recipeRepository)
    }

    private fun initFavoritesRecyclerView(repo: RecipeRepository) {
        repo.getFavoriteRecipes().observe(this, Observer { recipes ->

            adapter.swapData(recipes)

            val recyclerView = findViewById<RecyclerView>(R.id.favorites_list)
            recyclerView.adapter = adapter
        })
    }
}
