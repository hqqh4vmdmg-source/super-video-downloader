package com.myAllVideoBrowser.ui.component.binding

import VideoInfoAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myAllVideoBrowser.data.local.model.LocalVideo
import com.myAllVideoBrowser.data.local.model.Proxy
import com.myAllVideoBrowser.data.local.model.Suggestion
import com.myAllVideoBrowser.data.local.room.entity.HistoryItem
import com.myAllVideoBrowser.data.local.room.entity.PageInfo
import com.myAllVideoBrowser.data.local.room.entity.ProgressInfo
import com.myAllVideoBrowser.data.local.room.entity.VideoInfo
import com.myAllVideoBrowser.ui.component.adapter.*
import com.myAllVideoBrowser.ui.main.home.browser.webTab.WebTab

object RecyclerViewBinding {
    @BindingAdapter("items")
    @JvmStatic
    fun setWebTabs(view: RecyclerView, tabs: List<WebTab>) {
        with(view.adapter as WebTabsAdapter?) {
            this?.let { setData(tabs) }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setSuggestions(view: RecyclerView, items: List<Suggestion>) {
        with(view.adapter as SuggestionAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setProgressInfos(view: RecyclerView, items: List<ProgressInfo>) {
        with(view.adapter as ProgressAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setProxiesList(view: RecyclerView, items: List<Proxy>) {
        with(view.adapter as ProxiesAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setVideoInfos(view: RecyclerView, items: List<LocalVideo>) {
        with(view.adapter as VideoAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun historyItems(view: RecyclerView, items: List<HistoryItem>) {

        if (view.adapter is HistoryAdapter?) {
            with(view.adapter as HistoryAdapter?) {
                this?.let { setData(items) }
            }
        }
        if (view.adapter is HistorySearchAdapter?) {
            with(view.adapter as HistorySearchAdapter?) {
                this?.let { setData(items) }
            }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setDetectedVideoInfos(view: RecyclerView, items: List<VideoInfo>) {
        with(view.adapter as VideoInfoAdapter?) {
            this?.let { setData(items) }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setDetectedVideoInfosSet(view: RecyclerView, items: Set<VideoInfo>) {
        with(view.adapter as VideoInfoAdapter?) {
            this?.let { setData(items.toList()) }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setBookmarks(view: RecyclerView, items: MutableList<PageInfo>) {
        with(view.adapter as BookmarksAdapter?) {
            this?.let { setData(items) }
        }
    }
}
