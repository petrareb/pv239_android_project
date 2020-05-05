package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.TagsAdapter
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Tag
import com.example.receptarstarejmatere.database.repository.TagRepository
import com.example.receptarstarejmatere.utils.Constants

class TagsActivity: AppCompatActivity(), TagsAdapter.OnTagListener {

    private var mTags: ArrayList<Tag> = ArrayList()
    private lateinit var adapter: TagsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tags)

        adapter = TagsAdapter(onTagListener = this)
        initTagsRecyclerView(App.tagRepository)
    }

    private fun initTagsRecyclerView(tagRepository: TagRepository) {
        tagRepository.getAllTags().observe(this, Observer {tags ->
            mTags.clear()
            mTags.addAll(tags)

            adapter.swapData(tags)

            val recyclerView = findViewById<RecyclerView>(R.id.tags_list)
            recyclerView.adapter = adapter
        })
    }

    override fun onSelectedTagClick(position: Int) {
        val intent = Intent(this, RecipesActivity::class.java)
        intent.putExtra(Constants.SELECTED_TAG_ID, mTags[position].tagId)
        startActivity(intent)
    }
}
