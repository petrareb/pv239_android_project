package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.model.Recipe

class FavoritesAdapter(private val recipes: LiveData<List<Recipe>>/* = listOf()*/): RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    private var recipesList: List<Recipe> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.favorites_name)
        var star: ImageView = itemView.findViewById(R.id.favorites_star)

        fun bind(recipe: Recipe) {
            name.text = recipe.name
            star.setImageResource(R.drawable.ic_star_yellow_24dp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        recipesList = recipes.value as ArrayList<Recipe>
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favorites_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return recipes.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(recipesList[position])
    }
}