package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.viewModel.IngredientViewModel

class EditIngredientsAdapter(private var ingredients: List<IngredientViewModel> = listOf())
    : RecyclerView.Adapter<EditIngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.edit_ingredient_list_item,
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

    fun addItem(position: Int) {
        notifyItemInserted(position)
    }

    class ViewHolder(ingredientsView: View)
        : RecyclerView.ViewHolder(ingredientsView) {
        var ingredName: EditText = ingredientsView.findViewById(R.id.edit_ingred_name)
        var ingredQuantity: EditText = ingredientsView.findViewById(R.id.edit_ingred_quantity)
        var ingredMeasure: EditText = ingredientsView.findViewById(R.id.edit_ingred_measure)

        fun bind(ingred: IngredientViewModel) {
            ingredName.setText(ingred.name)
            ingredMeasure.setText(ingred.measure)
            ingredQuantity.setText(ingred.quantity)
        }
    }
}