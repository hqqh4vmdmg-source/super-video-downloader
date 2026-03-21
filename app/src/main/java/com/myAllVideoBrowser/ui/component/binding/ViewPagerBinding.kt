package com.myAllVideoBrowser.ui.component.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager

object ViewPagerBinding {

    @BindingAdapter("offScreenPageLimit")
    @JvmStatic
    fun setOffScreenPageLimit(view: ViewPager, pageLimit: Int) {
        view.offscreenPageLimit = pageLimit
    }
}
