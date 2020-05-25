package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.RecipesAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.utils.Constants
import com.example.receptarstarejmatere.utils.ListRecipesActivity
import com.example.receptarstarejmatere.utils.SwipeRecipeCallback
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipesActivity : AppCompatActivity(), RecipesAdapter.OnRecipeListener {

    private var mRecipes: ArrayList<Recipe> = ArrayList()
    private lateinit var adapter: RecipesAdapter
    private lateinit var activityType: ListRecipesActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        setActivityType()

        adapter = RecipesAdapter(onRecipeListener = this, context = this)

        when (activityType) {
            ListRecipesActivity.FAVORITES -> {
                initFavoritesActivity()
            }
            ListRecipesActivity.SEARCHES -> {
                initSearchResultsActivity()
            }
            else -> {
                initRecipesAccordingTagActivity()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        intent.removeExtra(Constants.FAVORITE_RECIPES)
        intent.removeExtra(Constants.SEARCH_QUERY)
    }

    override fun onRecipeDelete() {
        Toast.makeText(
            this,
            resources.getString(R.string.success_delete_recipe),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setActivityType() {
        if (intent?.getBooleanExtra(Constants.FAVORITE_RECIPES, false) == true) {
            activityType = ListRecipesActivity.FAVORITES
            return
        }
        if (!intent?.getStringExtra(Constants.SEARCH_QUERY).isNullOrEmpty()) {
            activityType = ListRecipesActivity.SEARCHES
            return
        }
        activityType = ListRecipesActivity.ACCORDING_TAG
    }

    private fun initFavoritesActivity() {
        recipes_header.text = getString(R.string.favorites)
        initFavoritesRecyclerView()
        no_recipes_found.text = getString(R.string.no_favorite_recipes_found)
    }

    private fun initSearchResultsActivity() {
        val header =
            getString(R.string.search_header) + " \"" + intent?.getStringExtra(Constants.SEARCH_QUERY) + "\""
        recipes_header.text = header
        doSearch(intent?.getStringExtra(Constants.SEARCH_QUERY).orEmpty())
    }

    private fun initRecipesAccordingTagActivity() {
        recipes_header.text = intent?.getStringExtra(Constants.SELECTED_TAG_NAME)
        initRecipesRecyclerView()

        val deleteTagButton = delete_tag_button
        deleteTagButton.visibility = View.VISIBLE
        deleteTagButton.setOnClickListener {
            if (tryDeleteTag()) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.success_delete_tags),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, TagsActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_delete_tags),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun tryDeleteTag(): Boolean {
        if (mRecipes.isNotEmpty()) {
            return false
        }
        GlobalScope.launch {
            val tagId = intent.getIntExtra(Constants.SELECTED_TAG_ID, 100)
            App.tagRepository.deleteTagById(tagId)
        }
        return true
    }

    private fun doSearch(query: String) {
        if (query.isEmpty()) {
            return
        }
        GlobalScope.launch {
            val foundRecipes = App.searchService.getRecipesContainingString(query)
            initRecipesRecyclerShared(foundRecipes)
        }
    }

    private fun initRecipesRecyclerView() {
        val selectedTagId = intent.getIntExtra(Constants.SELECTED_TAG_ID, 100)
        App.recipeTagRepository.getTagWithRecipes(selectedTagId)
            .observe(this, Observer { tagWithRecipes ->
                val recipes: List<Recipe> = tagWithRecipes?.recipes ?: listOf()
                initRecipesRecyclerShared(recipes)
            })
    }

    private fun initFavoritesRecyclerView() {
        App.recipeRepository.getFavoriteRecipes().observe(this, Observer { favorites ->
            initRecipesRecyclerShared(favorites)
        })
    }

    private fun initRecipesRecyclerShared(recipes: List<Recipe> = listOf()) {
        mRecipes.clear()
        mRecipes.addAll(recipes)

        adapter.swapData(mRecipes)

        val recyclerView = recipes_list
        recyclerView.adapter = adapter

        if (activityType != ListRecipesActivity.SEARCHES) {
            val itemTouchHelper = ItemTouchHelper(SwipeRecipeCallback(adapter, this))
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

        if (mRecipes.isEmpty()) {
            no_recipes_found.visibility = View.VISIBLE
        } else {
            no_recipes_found.visibility = View.GONE
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
