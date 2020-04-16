package com.example.receptarstarejmatere.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.FavoritesAdapter
import com.example.receptarstarejmatere.database.DataGenerator
import com.example.receptarstarejmatere.database.repository.RecipeRepository
import java.util.*

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val repo = RecipeRepository(application)
//        val db = MyDb.invoke(this)
//        insertTestData(db)
//        initFavoritesRecyclerView(db)

        insertTestData(repo)
        initFavoritesRecyclerView(repo)

    }

//    private fun initFavoritesRecyclerView(db : MyDb) {
//        val adapter = FavoritesAdapter(db.recipeDao().getAll())
//        val recyclerView = findViewById<RecyclerView>(R.id.favorites_list)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//    }

//    private fun insertTestData(db : MyDb){
//        var recipes = DataGenerator.generateRecipes()
//        var tags = DataGenerator.generateTags()
//        var recdpesTagsCrossRefs = DataGenerator.generateRecipesTagsCrossRef()
//        db.recipeDao().insertAll(Collections.unmodifiableList(recipes))
//        db.tagDao().insertAll(Collections.unmodifiableList(tags))
//        db.recipeTagDao().insertAll(Collections.unmodifiableList(recipesTagsCrossRefs))
//    }

    private fun insertTestData(repo : RecipeRepository){
        var recipes = DataGenerator.generateRecipes()
        repo.insertAll(Collections.unmodifiableList(recipes))
    }

    private fun initFavoritesRecyclerView(repo: RecipeRepository) {
        val adapter = FavoritesAdapter(repo?.getRecipes())
        val recyclerView = findViewById<RecyclerView>(R.id.favorites_list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}