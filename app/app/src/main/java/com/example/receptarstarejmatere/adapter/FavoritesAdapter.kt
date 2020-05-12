package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.model.Recipe

class FavoritesAdapter(private var recipes: List<Recipe> = listOf(), private var onFavoriteListener: OnFavoriteRecipeListener)
    : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.favorites_list_item,
                parent,
                false
            ),
            onFavoriteListener
        )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(recipes[position])
    }

    fun swapData(newData: List<Recipe>) {
        recipes = newData
        notifyDataSetChanged()
    }

    class ViewHolder(
        private var favoriteView: View,
        private var onFavoriteListener: OnFavoriteRecipeListener
    ) : RecyclerView.ViewHolder(favoriteView), View.OnClickListener {

        var name: TextView = favoriteView.findViewById(R.id.favorites_name)
        var star: ImageView = favoriteView.findViewById(R.id.favorites_star)

        fun bind(recipe: Recipe) {
            name.text = recipe.name
            star.setImageResource(R.drawable.ic_star_yellow_24dp)

            favoriteView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onFavoriteListener.onSelectedFavoriteRecipeClick(adapterPosition)
        }
    }

    interface OnFavoriteRecipeListener {
        fun onSelectedFavoriteRecipeClick(position: Int)
    }
}