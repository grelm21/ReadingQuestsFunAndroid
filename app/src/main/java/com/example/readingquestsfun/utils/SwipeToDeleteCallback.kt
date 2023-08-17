package com.example.readingquestsfun.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.readingquestsfun.R

abstract class SwipeToDeleteCallback(private val context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteBackground = ColorDrawable(Color.TRANSPARENT)
    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.baseline_delete_50)
    private val intrinsicWidth = deleteIcon?.intrinsicWidth ?: 0
    private val intrinsicHeight = deleteIcon?.intrinsicHeight ?: 0
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled){
            clearCanvas(canvas, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        deleteBackground.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )

        deleteBackground.draw(canvas)

        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconRight = if (itemView.right < intrinsicWidth / 2) itemView.right + (dX / 8).toInt() else itemView.right + (dX / 8).toInt() + (intrinsicWidth / 2)
        val deleteIconLeft = deleteIconRight - intrinsicWidth
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        deleteIcon?.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)

        val dxPercent = (dX / 4) / 100
        val alphaPerDx = 255 / dxPercent

        Log.i("ITEM_RIGHT", (dxPercent.toInt() * alphaPerDx.toInt()).toString())

        deleteIcon?.alpha = if (dX > -255 || dX < 0) dxPercent.toInt() * alphaPerDx.toInt() else 255
        deleteIcon?.draw(canvas)

        super.onChildDraw(canvas, recyclerView, viewHolder, dX / 4, dY, actionState, isCurrentlyActive)
    }
    private fun clearCanvas(canvas: Canvas?, left: Float, top: Float, right: Float, bottom: Float){
        canvas?.drawRect(left, top, right, bottom, clearPaint)
    }
}