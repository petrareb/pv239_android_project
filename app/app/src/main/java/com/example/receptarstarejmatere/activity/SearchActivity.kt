package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.utils.Constants
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        button_search.setOnClickListener {
            val text = search_text.text.toString()
            if (text.isEmpty()) {
                search_text.error = resources.getString(R.string.search_error_no_query)
            } else {
                val intent = Intent(this, RecipesActivity::class.java)
                intent.putExtra(Constants.SEARCH_QUERY, text)
                startActivity(intent)
            }
        }
    }
}
