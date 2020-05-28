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


class SwipeRecipeCallback(
    private val adapter: RecipesAdapter,
    context: Context
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val backgroundDelete: ColorDrawable = ColorDrawable(Color.RED)
    private val iconDelete: Drawable? = getDrawable(context, R.drawable.ic_delete_black)

    private val backgroundEdit: ColorDrawable = ColorDrawable(Color.YELLOW)
    private val iconEdit: Drawable? = getDrawable(context, R.drawable.ic_edit)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            adapter.deleteItem(position)
        } else if (direction == ItemTouchHelper.RIGHT) {
            adapter.editItem(position)
        }
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

        val iconMargin: Int = (itemView.height - (iconDelete?.intrinsicHeight ?: 0)) / 2
        val iconTop: Int = itemView.top + (itemView.height - (iconDelete?.intrinsicHeight ?: 0)) / 2
        val iconBottom: Int = iconTop + (iconDelete?.intrinsicHeight ?: 0)

        when {
            dX > 0 -> {
                var iconLeft = 0
                var iconRight = 0
                if (dX > iconMargin) {
                    iconLeft = itemView.left + iconMargin
                    iconRight = itemView.left + iconMargin + (iconEdit?.intrinsicWidth ?: 0)
                }

                iconEdit?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                backgroundEdit.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset,
                    itemView.bottom
                )
            }
            dX < 0 -> {
                var iconLeft: Int = itemView.right - iconMargin - (iconDelete?.intrinsicWidth ?: 0)
                var iconRight: Int = itemView.right - iconMargin
                if (dY > itemView.right - iconMargin - (iconDelete?.intrinsicWidth ?: 0)) {
                    iconLeft = 0
                    iconRight = 0
                }

                iconDelete?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                backgroundDelete.setBounds(
                    itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
            }
            else -> { // unSwiped
                iconDelete?.setBounds(0, 0, 0, 0)
                iconEdit?.setBounds(0, 0, 0, 0)
                backgroundDelete.setBounds(0, 0, 0, 0)
            }
        }

        backgroundDelete.draw(c)
        iconDelete?.draw(c)
        backgroundEdit.draw(c)
        iconEdit?.draw(c)
    }
}