package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.model.Tag

class TagsAdapter(
    private var tags: List<Tag> = listOf(),
    private var onTagListener: OnTagListener
) : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tags_list_item,
                parent,
                false
            ),
            onTagListener
        )
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(tags[position])
    }

    fun swapData(newData: List<Tag>) {
        tags = newData
        notifyDataSetChanged()
    }

    class ViewHolder(private var tagsView: View, private var onTagListener: OnTagListener) :
        RecyclerView.ViewHolder(tagsView), View.OnClickListener {
        var tagName: TextView = tagsView.findViewById(R.id.tag_name)

        fun bind(tag: Tag) {
            tagName.text = tag.name
            tagsView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onTagListener.onSelectedTagClick(adapterPosition)
        }
    }

    interface OnTagListener {
        fun onSelectedTagClick(position: Int)
    }
}