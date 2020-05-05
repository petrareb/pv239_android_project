package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.model.Recipe

class RecipesAdapter(private var recipes: List<Recipe> = listOf(), private var onRecipeListener: OnRecipeListener)
    : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recipes_list_item,
                parent,
                false
            ),
            onRecipeListener
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

    class ViewHolder(private var recipesView: View, private var onRecipeListener: OnRecipeListener)
        : RecyclerView.ViewHolder(recipesView), View.OnClickListener {

        var recipeName: TextView = recipesView.findViewById(R.id.recipe_name)
        var star: ImageView = recipesView.findViewById(R.id.recipes_star)

        fun bind(recipe: Recipe) {
            recipeName.text = recipe.name
            if (recipe.isFavorite) {
                star.setImageResource(R.drawable.ic_star_yellow_24dp)
            }
            recipesView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onRecipeListener.onSelectedRecipeClick(adapterPosition)
        }
    }

    interface OnRecipeListener {
        fun onSelectedRecipeClick(position: Int)
    }
}