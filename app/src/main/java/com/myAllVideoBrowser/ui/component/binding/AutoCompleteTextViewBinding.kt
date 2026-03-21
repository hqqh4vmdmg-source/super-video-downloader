package com.myAllVideoBrowser.ui.component.binding

import androidx.databinding.BindingAdapter
import android.widget.AutoCompleteTextView
import com.myAllVideoBrowser.data.local.model.Suggestion
import com.myAllVideoBrowser.data.local.room.entity.HistoryItem
import com.myAllVideoBrowser.ui.component.adapter.SuggestionAdapter
import com.myAllVideoBrowser.ui.component.adapter.TabSuggestionAdapter

object AutoCompleteTextViewBinding {

    @BindingAdapter("items")
    @JvmStatic
    fun setSuggestions(view: AutoCompleteTextView, items: List<Suggestion>?) {
        with(view.adapter as SuggestionAdapter?) {
            if (items != null) {
                this?.setData(items)
            } else {
                this?.setData(emptyList())
            }
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setTabSuggestions(view: AutoCompleteTextView, items: List<HistoryItem>?) {
        with(view.adapter as TabSuggestionAdapter?) {
            if (items != null) {
                this?.setData(items)
            } else {
                this?.setData(emptyList())
            }
        }
    }
}
