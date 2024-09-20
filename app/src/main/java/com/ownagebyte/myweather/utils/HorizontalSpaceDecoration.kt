package com.ownagebyte.myweather.utils

import android.graphics.Rect
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpaceDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val halfSpace = space / 4

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = space
            outRect.right = halfSpace
        } else if (parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount?.minus(1) ?: -1)) {
            outRect.left = halfSpace
            outRect.right = space
        } else {
            outRect.left = halfSpace
            outRect.right = halfSpace
        }
    }
}
