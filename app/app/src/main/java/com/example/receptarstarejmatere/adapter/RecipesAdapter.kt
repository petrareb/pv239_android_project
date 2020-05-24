package com.example.receptarstarejmatere.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Recipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RecipesAdapter(
    private var recipes: List<Recipe> = listOf(),
    private var onRecipeListener: OnRecipeListener,
    private var context: Context
) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recipes_list_item,
                parent,
                false
            ),
            onRecipeListener,
            context
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
        onRecipeListener.onRecipeDelete()
    }

    fun editItem(position: Int) {
        notifyItemChanged(position)
        onRecipeListener.onSelectedRecipeEditSwipe(position)
    }

    class ViewHolder(
        private var recipesView: View,
        private var onRecipeListener: OnRecipeListener,
        private var context: Context
    ) : RecyclerView.ViewHolder(recipesView), View.OnClickListener {

        var recipeName: TextView = recipesView.findViewById(R.id.recipe_name)

        fun bind(recipe: Recipe) {
            recipeName.text = recipe.name
            val img: Drawable? = if (recipe.isFavorite) {
                ContextCompat.getDrawable(context, R.drawable.ic_heart_checked)
            } else {
                ContextCompat.getDrawable(context, R.drawable.ic_text_blue)
            }
            img?.setBounds(0, 0, 60, 60)
            recipeName.setCompoundDrawables(img, null, null, null)
            recipeName.compoundDrawablePadding = 18
            recipesView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onRecipeListener.onSelectedRecipeClick(adapterPosition)
        }
    }

    interface OnRecipeListener {
        fun onSelectedRecipeClick(position: Int)
        fun onSelectedRecipeEditSwipe(position: Int)
        fun onRecipeDelete()
    }
}