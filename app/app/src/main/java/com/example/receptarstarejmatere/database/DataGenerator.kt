package com.example.receptarstarejmatere.database

import android.content.Context
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Tag

class DataGenerator(private val context: Context) {

    suspend fun generateTags() {
        val tagNames = listOf(
            context.resources.getString(R.string.tag_sweet),
            context.resources.getString(R.string.tag_salty),
            context.resources.getString(R.string.tag_meatfree),
            context.resources.getString(R.string.tag_breakfast),
            context.resources.getString(R.string.tag_cakes),
            context.resources.getString(R.string.tag_vegan),
            context.resources.getString(R.string.tag_drinks),
            context.resources.getString(R.string.tag_salads),
            context.resources.getString(R.string.tag_dinner),
            context.resources.getString(R.string.tag_raw)
        )
        tagNames.forEach { tagName ->
            val tag = Tag(tagName)
            App.tagRepository.insert(tag)
        }
    }
}