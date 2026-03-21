package com.myAllVideoBrowser.ui.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.github.marlonlom.utilities.timeago.TimeAgoMessages
import com.myAllVideoBrowser.R
import com.myAllVideoBrowser.data.local.room.entity.HistoryItem
import com.myAllVideoBrowser.databinding.ItemHistorySearchBinding
import com.myAllVideoBrowser.util.ContextUtils
import java.util.*

class HistorySearchAdapter(
    private val historyListener: HistoryListener
) : ListAdapter<HistoryItem, HistorySearchAdapter.HistorySearchViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem) =
                oldItem == newItem
        }
    }

    class HistorySearchViewHolder(val binding: ItemHistorySearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historyItem: HistoryItem, historyListener: HistoryListener) {
            with(binding) {
                this.historyItem = historyItem
                this.historyListener = historyListener
                this.historyId = historyItem.id
                this.tvTime.text = convertTimeToTimeAgo(historyItem.datetime)

                if (historyItem.faviconBitmap() == null) {
                    val bm = AppCompatResources.getDrawable(
                        ContextUtils.getApplicationContext(),
                        R.drawable.ic_browser
                    )
                    this.favicon.setImageDrawable(bm)
                }

                executePendingBindings()
            }
        }

        private fun convertTimeToTimeAgo(time: Long): String {
            val locale: Locale = Locale.getAvailableLocales().first()
            val messages: TimeAgoMessages =
                TimeAgoMessages.Builder().withLocale(locale).build()
            return TimeAgo.using(time, messages)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorySearchViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistorySearchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history_search, parent, false
        )
        return HistorySearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistorySearchViewHolder, position: Int) =
        holder.bind(getItem(position), historyListener)

    fun setData(historyItems: List<HistoryItem>) {
        submitList(historyItems.asReversed())
    }
}
