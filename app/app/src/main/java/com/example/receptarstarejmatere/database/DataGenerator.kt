package com.example.receptarstarejmatere.database

import android.content.Context
import android.content.res.Resources
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.*
import com.example.receptarstarejmatere.database.repository.TagRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

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

//                "Sladké",
//                "Slané",
//                "Bez mäsa",
//                "Raňajky",
//                "Koláče",
//                "Vegan",
//                "Drinky",
//                "Šaláty",
//                "Večera",
//                "Raw"
        )
        tagNames.forEach { tagName ->
            val tag = Tag(tagName)
            App.tagRepository.insert(tag)
        }
    }
}