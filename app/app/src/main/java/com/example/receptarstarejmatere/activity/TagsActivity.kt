package com.example.receptarstarejmatere.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.TagsAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.repository.TagRepository

class TagsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tags)

        initTagsRecyclerView(App.tagRepository)
    }

    private fun initTagsRecyclerView(tagRepository: TagRepository) {
        tagRepository.getAllTags().observe(this, Observer {tags ->
            val adapter = TagsAdapter(tags)
            val recyclerView = findViewById<RecyclerView>(R.id.tags_list)
            recyclerView.adapter = adapter
        })
    }
}

// TODO - iba 1 reused adapter ako vo FavoritesActivity