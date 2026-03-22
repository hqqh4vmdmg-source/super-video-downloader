package com.myAllVideoBrowser.ui.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myAllVideoBrowser.R
import com.myAllVideoBrowser.databinding.ItemWebTabButtonBinding
import com.myAllVideoBrowser.ui.main.home.browser.webTab.WebTab

interface WebTabsListener {
    fun onCloseTabClicked(webTab: WebTab)
    fun onSelectTabClicked(webTab: WebTab)
}

class WebTabsAdapter(
    private val webTabsListener: WebTabsListener
) : ListAdapter<WebTab, WebTabsAdapter.WebTabsViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WebTab>() {
            override fun areItemsTheSame(oldItem: WebTab, newItem: WebTab) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WebTab, newItem: WebTab) =
                oldItem == newItem
        }
    }

    class WebTabsViewHolder(val binding: ItemWebTabButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(webTab: WebTab, webTabsListener: WebTabsListener) {
            with(binding) {
                val context = this.root.context

                this.webTab = webTab
                this.tabListener = webTabsListener

                this.closeTab.visibility = if (webTab.isHome()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
                if (webTab.favicon == null && !webTab.isHome()) {
                    val bm = AppCompatResources.getDrawable(context, R.drawable.public_24px)
                    this.faviconTab.setImageDrawable(bm)
                }

                if (webTab.isHome()) {
                    val bm = AppCompatResources.getDrawable(context, R.drawable.home_48px)
                    this.faviconTab.setImageDrawable(bm)
                }

                if (!webTab.isHome()) {
                    this.tabTitle.text = if (webTab.title.isEmpty()) {
                        webTab.url
                    } else {
                        webTab.title
                    }
                }

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebTabsViewHolder {
        val binding = DataBindingUtil.inflate<ItemWebTabButtonBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_web_tab_button, parent, false
        )
        return WebTabsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WebTabsViewHolder, position: Int) =
        holder.bind(getItem(position), webTabsListener)

    fun setData(webTabs: List<WebTab>) {
        submitList(webTabs)
    }
}
