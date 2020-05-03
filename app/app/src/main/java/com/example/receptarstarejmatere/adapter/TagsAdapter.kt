package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.model.Tag

class TagsAdapter(private var tags: List<Tag> = listOf()): RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    class ViewHolder(tagsView: View): RecyclerView.ViewHolder(tagsView) {
        var tagName: TextView = tagsView.findViewById(R.id.tag_name)

        fun bind(tag: Tag) {
            tagName.text = tag.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tags_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(tags[position])
    }


}