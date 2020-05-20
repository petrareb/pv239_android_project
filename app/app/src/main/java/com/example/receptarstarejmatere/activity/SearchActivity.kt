package com.example.receptarstarejmatere.activity

import android.app.Activity
import android.app.DownloadManager
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.RecipesAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import com.example.receptarstarejmatere.utils.Constants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var adapter: RecipesAdapter
    private lateinit var searchDialog: androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // enable type-to-search
        setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchDialog = findViewById(R.id.search_dialog)
        searchDialog.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            // Do not iconify the widget; expand it by default
            setIconifiedByDefault(false)
        }

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                val intent = Intent(this, RecipesActivity::class.java)
                intent.putExtra(Constants.SEARCH_QUERY, query)
                startActivity(intent)
            }
        }
    }
}
