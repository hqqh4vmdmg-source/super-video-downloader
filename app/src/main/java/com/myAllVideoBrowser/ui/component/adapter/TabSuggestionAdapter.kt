package com.myAllVideoBrowser.ui.component.adapter

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.myAllVideoBrowser.data.local.room.entity.HistoryItem
import com.myAllVideoBrowser.databinding.ItemTabSuggestionBinding

interface SuggestionTabListener {
    fun onItemClicked(suggestion: HistoryItem)
}

class TabSuggestionAdapter(
    context: Context,
    private var suggestions: List<HistoryItem>,
    private val suggestionsListener: SuggestionTabListener?
) : ArrayAdapter<HistoryItem>(context, 0) {

    override fun getCount() = suggestions.size

    override fun getItem(position: Int): HistoryItem {
        return try {
            suggestions[position]
        } catch (_: IndexOutOfBoundsException) {
            HistoryItem(url = "")
        }
    }

    override fun getItemId(position: Int) = try {
        suggestions[position].hashCode().toLong()
    } catch (_: IndexOutOfBoundsException) {
        0L
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        val binding = if (view == null) {
            val inflater = LayoutInflater.from(viewGroup.context)
            ItemTabSuggestionBinding.inflate(inflater, viewGroup, false)
        } else {
            DataBindingUtil.getBinding(view) ?: run {
                val inflater = LayoutInflater.from(viewGroup.context)
                ItemTabSuggestionBinding.inflate(inflater, viewGroup, false)
            }
        }

        with(binding) {
            this.suggestion = suggestions[position]
            this.listener = suggestionsListener
            executePendingBindings()
        }

        return binding.root
    }

    fun setData(suggestions: List<HistoryItem>) {
        this.suggestions = suggestions
        notifyDataSetChanged()
    }
}
