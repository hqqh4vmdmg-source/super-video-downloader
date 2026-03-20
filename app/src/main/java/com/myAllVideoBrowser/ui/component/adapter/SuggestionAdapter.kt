package com.myAllVideoBrowser.ui.component.adapter

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.myAllVideoBrowser.data.local.model.Suggestion
import com.myAllVideoBrowser.databinding.ItemSuggestionBinding

interface SuggestionListener {
    fun onItemClicked(suggestion: Suggestion)
}
class SuggestionAdapter(
    context: Context,
    private var suggestions: List<Suggestion>,
    private val suggestionListener: SuggestionListener
) : ArrayAdapter<Suggestion>(context, 0) {

    override fun getCount() = suggestions.size

    override fun getItem(position: Int): Suggestion {
        return try {
            suggestions[position]
        } catch (_: IndexOutOfBoundsException) {
            Suggestion()
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
            ItemSuggestionBinding.inflate(inflater, viewGroup, false)
        } else {
            DataBindingUtil.getBinding(view) ?: run {
                val inflater = LayoutInflater.from(viewGroup.context)
                ItemSuggestionBinding.inflate(inflater, viewGroup, false)
            }
        }

        with(binding) {
            this.suggestion = suggestions[position]
            this.listener = suggestionListener
            executePendingBindings()
        }

        return binding.root
    }

    fun setData(suggestions: List<Suggestion>) {
        this.suggestions = suggestions
        notifyDataSetChanged()
    }
}
