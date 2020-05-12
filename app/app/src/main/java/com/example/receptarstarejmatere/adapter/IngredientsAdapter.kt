package com.example.receptarstarejmatere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.database.model.IngredientWithMeasure

class IngredientsAdapter(private var ingredientsWithMeasures: List<IngredientWithMeasure> = listOf())
    : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.ingr_measure_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return ingredientsWithMeasures.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(ingredientsWithMeasures[position])
    }

    fun swapData(newData: List<IngredientWithMeasure>) {
        ingredientsWithMeasures = newData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var measure: TextView = itemView.findViewById(R.id.ingred_measure)
        var quantity: TextView = itemView.findViewById(R.id.ingred_quantity)
        var name : TextView = itemView.findViewById(R.id.ingred_name)

        fun bind(ingred: IngredientWithMeasure) {
            name.text = ingred.name
            measure.text = ingred.measure
            quantity.text = ingred.quantity.toString()
        }
    }
}

