package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.viewModel.IngredientViewModel

class IngredientsAdapter(private var ingredients: List<IngredientViewModel> = listOf())
    : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.ingredients_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return ingredients.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(ingredients[position])
    }

    fun swapData(newData: List<IngredientViewModel>) {
        ingredients = newData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ingred: TextView = itemView.findViewById(R.id.ingredient)

        fun bind(ingredient: IngredientViewModel) {
            val text = ingredient.quantity + " " + ingredient.measure + " " + ingredient.name
            ingred.text = text
        }
    }
}