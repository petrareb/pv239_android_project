package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritesAdapter(private var recipes: MutableList<Recipe> = mutableListOf(), private var onFavoriteListener: OnFavoriteRecipeListener)
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

    fun swapData(newData: MutableList<Recipe>) {
        recipes = newData
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        GlobalScope.launch {
            val recipeToDelete = recipes[position]
            App.recipeRepository.deleteRecipe(recipeToDelete)
        }

        notifyItemRemoved(position)
    }

    fun editItem(position: Int) {
        notifyItemChanged(position)
        onFavoriteListener.onSelectedRecipeEditSwipe(position)
    }


    class ViewHolder(
        private var favoriteView: View,
        private var onFavoriteListener: OnFavoriteRecipeListener
    ) : RecyclerView.ViewHolder(favoriteView), View.OnClickListener{

        var name: TextView = favoriteView.findViewById(R.id.favorites_name)

        fun bind(recipe: Recipe) {
            name.text = recipe.name

            favoriteView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onFavoriteListener.onSelectedFavoriteRecipeClick(adapterPosition)
        }
    }

    interface OnFavoriteRecipeListener {
        fun onSelectedFavoriteRecipeClick(position: Int)
        fun onSelectedRecipeEditSwipe(position: Int)
    }
}