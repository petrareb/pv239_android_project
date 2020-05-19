package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.viewModel.TagViewModel

class RecipeTagsAdapter(private var tags: List<TagViewModel> = listOf(), private val onTagListener: OnSelectTagListener)
    : RecyclerView.Adapter<RecipeTagsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.new_recipe_tags_list_item,
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

    fun swapData(newData: List<TagViewModel>) {
        tags = newData
        notifyDataSetChanged()
    }

    class ViewHolder(tagsView: View, private val onSelectTagListener: OnSelectTagListener)
        : RecyclerView.ViewHolder(tagsView) {

        var tagWithCheck: CheckedTextView = tagsView.findViewById(R.id.new_recipe_tags_item)

        fun bind(tagModel: TagViewModel) {
            tagWithCheck.text = tagModel.tagName
            tagWithCheck.isChecked = tagModel.isSelected
            tagWithCheck.setOnClickListener {
                tagModel.isSelected = !tagModel.isSelected
                tagWithCheck.isChecked= tagModel.isSelected
                onSelectTagListener.onSelectTag()
            }
        }
    }

    interface OnSelectTagListener {
        fun onSelectTag()
    }
}