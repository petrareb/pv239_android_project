package com.example.receptarstarejmatere.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.FavoritesAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.repository.RecipeRepository

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        initFavoritesRecyclerView(App.recipe_repository)
    }

    private fun initFavoritesRecyclerView(repo: RecipeRepository) {
        val recipesFromDb = repo.getFavoriteRecipes(this)
        val adapter = FavoritesAdapter(recipesFromDb)
        val recyclerView = findViewById<RecyclerView>(R.id.favorites_list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}