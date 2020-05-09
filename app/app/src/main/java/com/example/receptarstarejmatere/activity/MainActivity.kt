package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.receptarstarejmatere.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var showedAddButtons: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        button_favorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
        button_recipes.setOnClickListener {
            val intent = Intent(this, TagsActivity::class.java)
            startActivity(intent)
        }
        button_add_new.setOnClickListener {
            toggleImageViewVisibility()
        }
        button_add_new_recipe.setOnClickListener {
            val intent = Intent(this, NewRecipeActivity::class.java)
            startActivity(intent)
        }
        button_add_new_tag.setOnClickListener {
            val intent = Intent(this, NewTagActivity::class.java)
            startActivity(intent)
        }
        button_settings.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

//        val ing = ArrayList<RecipeWithIngredients>()
//        App.recipeIngredientRepository.getAll().observe(this, Observer { recipes ->
//            ing.addAll(recipes)
//        })

        hideButtons()
    }

    override fun onStart() {
        super.onStart()
        hideButtons()
    }

    override fun onResume() {
        super.onResume()
        hideButtons()
    }

    private fun toggleImageViewVisibility() {
        if (!showedAddButtons) {
            showButtons()
        } else {
            hideButtons()
        }
    }

    private fun hideButtons() {
        showedAddButtons = false
        button_add_new_tag.visibility = View.GONE
        button_add_new_recipe.visibility = View.GONE
    }

    private fun showButtons() {
        showedAddButtons = true
        button_add_new_recipe.visibility = View.VISIBLE
        button_add_new_tag.visibility = View.VISIBLE
    }
}
