package com.example.receptarstarejmatere.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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

    fun deleteItem(position: Int) {
        GlobalScope.launch {
            val recipeToDelete = recipes[position]
            App.recipeRepository.deleteRecipe(recipeToDelete)
        }

        notifyItemRemoved(position)
    }

    fun editItem(position: Int) {
        notifyItemChanged(position)
        onRecipeListener.onSelectedRecipeEditSwipe(position)
    }

    class ViewHolder(private var recipesView: View, private var onRecipeListener: OnRecipeListener)
        : RecyclerView.ViewHolder(recipesView), View.OnClickListener {

        var recipeName: TextView = recipesView.findViewById(R.id.recipe_name)
        var img: Drawable = recipesView.resources.getDrawable(R.drawable.ic_star_yellow_24dp)
        fun bind(recipe: Recipe) {
            recipeName.text = recipe.name
            if (recipe.isFavorite) {
                img.setBounds( 0, 0, 60, 60 )
                recipeName.setCompoundDrawables(img,null, null, null)
                recipeName.compoundDrawablePadding = 18
            }
            recipesView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onRecipeListener.onSelectedRecipeClick(adapterPosition)
        }
    }

    interface OnRecipeListener {
        fun onSelectedRecipeClick(position: Int)
        fun onSelectedRecipeEditSwipe(position: Int)
    }
}