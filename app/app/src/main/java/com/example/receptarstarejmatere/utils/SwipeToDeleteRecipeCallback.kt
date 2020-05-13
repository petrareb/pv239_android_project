package com.example.receptarstarejmatere.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.adapter.RecipesAdapter


class SwipeToDeleteRecipeCallback(private val adapter: RecipesAdapter, context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val background: ColorDrawable = ColorDrawable(Color.RED)
    private val icon: Drawable? = getDrawable(context, R.drawable.ic_delete)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.deleteItem(position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin: Int = (itemView.height - (icon?.intrinsicHeight ?: 0)) / 2
        val iconTop: Int = itemView.top + (itemView.height - (icon?.intrinsicHeight ?: 0)) / 2
        val iconBottom: Int = iconTop + (icon?.intrinsicHeight ?: 0)


//        if (dX > 0) { } // TODO Swiping to the right -> later for EDIT

        if (dX < 0) {
            val iconLeft: Int = itemView.right - iconMargin - (icon?.intrinsicWidth ?: 0)
            val iconRight: Int = itemView.right - iconMargin

            icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top,
                itemView.right,
                itemView.bottom
            )
        } else { // unSwiped
            background.setBounds(0, 0, 0, 0);

        }
        background.draw(c)
        icon?.draw(c)
    }
}