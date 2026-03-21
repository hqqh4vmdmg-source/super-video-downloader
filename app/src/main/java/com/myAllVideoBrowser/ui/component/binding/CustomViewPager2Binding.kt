package com.myAllVideoBrowser.ui.component.binding

import androidx.annotation.OptIn
import androidx.databinding.BindingAdapter
import androidx.media3.common.util.UnstableApi
import com.myAllVideoBrowser.ui.main.home.browser.BrowserFragment
import com.myAllVideoBrowser.ui.main.home.browser.CustomViewPager2
import com.myAllVideoBrowser.ui.main.home.browser.webTab.WebTab

object CustomViewPager2Binding {

    @OptIn(UnstableApi::class)
    @BindingAdapter("items")
    @JvmStatic
    fun setWebItems(view: CustomViewPager2, currentItems: List<WebTab>?) {
        with(view.adapter as BrowserFragment.TabsFragmentStateAdapter?) {
            this?.setRoutes(currentItems ?: emptyList())
        }
    }

    @BindingAdapter("offScreenPageLimit")
    @JvmStatic
    fun setOffScreenPageLimit(view: CustomViewPager2, pageLimit: Int) {
        view.offscreenPageLimit = pageLimit
    }

    @BindingAdapter("currentItem")
    @JvmStatic
    fun setCurrentItem(view: CustomViewPager2, currentItemPosition: Int) {
        view.currentItem = currentItemPosition
    }
}
