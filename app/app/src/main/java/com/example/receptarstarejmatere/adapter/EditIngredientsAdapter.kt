package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.viewModel.IngredientViewModel

class EditIngredientsAdapter(
    public var ingredients: MutableList<IngredientViewModel> = mutableListOf()
) : RecyclerView.Adapter<EditIngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.edit_ingredient_list_item,
                parent,
                false
            )
        )
        viewHolder.saveIngredButton.setOnClickListener {
            val editedIngredient = IngredientViewModel(
                name = viewHolder.ingredName.text.toString(),
                quantity = viewHolder.ingredQuantity.text.toString(),
                measure = viewHolder.ingredMeasure.text.toString()
            )
            editIngredient(viewHolder.adapterPosition, editedIngredient, ingredients)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return ingredients.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(ingredients[position])
    }

    fun swapData(newData: List<IngredientViewModel>) {
        ingredients = newData.toMutableList()
        notifyDataSetChanged()
    }

    private fun editIngredient(
        position: Int,
        editedItem: IngredientViewModel,
        ingredientsList: MutableList<IngredientViewModel>
    ) {
        var ingredientsCopy = ingredientsList.toMutableList()
        ingredientsCopy[position] = editedItem
        swapData(ingredientsCopy)
    }

    class ViewHolder(ingredientsView: View) : RecyclerView.ViewHolder(ingredientsView) {
        var ingredName: EditText = ingredientsView.findViewById(R.id.edit_ingred_name)
        var ingredQuantity: EditText = ingredientsView.findViewById(R.id.edit_ingred_quantity)
        var ingredMeasure: EditText = ingredientsView.findViewById(R.id.edit_ingred_measure)

        var saveIngredButton: ImageButton =
            ingredientsView.findViewById(R.id.edit_save_ingredient_button)

        fun bind(ingred: IngredientViewModel) {
            ingredName.setText(ingred.name)
            ingredMeasure.setText(ingred.measure)
            ingredQuantity.setText(ingred.quantity)
        }
    }
}