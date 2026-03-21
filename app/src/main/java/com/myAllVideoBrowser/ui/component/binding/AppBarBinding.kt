package com.myAllVideoBrowser.ui.component.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.AppBarLayout

object AppBarBinding {

    @BindingAdapter("smoothExpanded")
    @JvmStatic
    fun setExpanded(view: AppBarLayout, isExpanded: Boolean) {
        view.setExpanded(isExpanded, true)
    }
}
