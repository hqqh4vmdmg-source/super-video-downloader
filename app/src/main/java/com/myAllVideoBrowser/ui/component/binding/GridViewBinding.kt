package com.myAllVideoBrowser.ui.component.binding

import android.widget.GridView
import androidx.databinding.BindingAdapter
import com.myAllVideoBrowser.data.local.room.entity.PageInfo
import com.myAllVideoBrowser.ui.component.adapter.*

object GridViewBinding {
    @BindingAdapter("items")
    @JvmStatic
    fun setTopPages(view: GridView, items: List<PageInfo>) {
        with(view.adapter as TopPageAdapter?) {
            this?.let { setData(items) }
        }
    }
}
