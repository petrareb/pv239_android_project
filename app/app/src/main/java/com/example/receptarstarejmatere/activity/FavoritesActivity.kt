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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
// init adapter
        initFavoritesRecyclerView(App.recipeRepository)
    }

    private fun initFavoritesRecyclerView(repo: RecipeRepository) {
        repo.getFavoriteRecipes().observe(this, Observer { recipes ->
            val adapter = FavoritesAdapter(recipes)
            val recyclerView = findViewById<RecyclerView>(R.id.favorites_list)
            recyclerView.adapter = adapter
        })
            // adapter.swapData - setter + zavolat notifyDataSetChanged ...

    }
}
// TODO
// spravit iba 1 var adapter a init z param