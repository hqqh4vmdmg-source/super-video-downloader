package com.myAllVideoBrowser.ui.component.binding

import androidx.annotation.OptIn
import androidx.databinding.BindingAdapter
import androidx.media3.common.util.UnstableApi
import androidx.viewpager2.widget.ViewPager2
import com.myAllVideoBrowser.ui.main.home.browser.BrowserFragment
import com.myAllVideoBrowser.ui.main.home.browser.webTab.WebTab

object ViewPager2Binding {

    @OptIn(UnstableApi::class)
    @BindingAdapter("items")
    @JvmStatic
    fun setWebItems(view: ViewPager2, currentItems: List<WebTab>?) {
        with(view.adapter as BrowserFragment.TabsFragmentStateAdapter?) {
            this?.setRoutes(currentItems ?: emptyList())
        }
    }

    @BindingAdapter("offScreenPageLimit")
    @JvmStatic
    fun setOffScreenPageLimit(view: ViewPager2, pageLimit: Int) {
        view.offscreenPageLimit = pageLimit
    }
}
