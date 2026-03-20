package com.myAllVideoBrowser.ui.component.adapter

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.myAllVideoBrowser.R
import com.myAllVideoBrowser.data.local.room.entity.PageInfo
import com.myAllVideoBrowser.databinding.ItemTopPageBinding
import com.myAllVideoBrowser.util.ContextUtils

class TopPageAdapter(
    context: Context,
    private var pageInfos: List<PageInfo>,
    private val itemListener: TopPagesListener
) : ArrayAdapter<TopPageAdapter.TopPageViewHolder>(context, R.layout.item_top_page) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val binding: ItemTopPageBinding = if (view == null) {
            val inflater = LayoutInflater.from(parent.context)
            ItemTopPageBinding.inflate(inflater, parent, false)
        } else {
            DataBindingUtil.getBinding(view) ?: run {
                val inflater = LayoutInflater.from(parent.context)
                ItemTopPageBinding.inflate(inflater, parent, false)
            }
        }

        val info = pageInfos[position]
        binding.pageInfo = info
        binding.listener = itemListener
        val favicon = info.faviconBitmap()
        if (favicon != null) {
            binding.imgIcon.setImageBitmap(favicon)
        } else {
            val drawable = AppCompatResources.getDrawable(
                ContextUtils.getApplicationContext(), R.drawable.ic_browser
            )
            val iconColor = ContextCompat.getColor(
                ContextUtils.getApplicationContext(), R.color.color_gray_2
            )
            drawable?.colorFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                BlendModeColorFilter(iconColor, BlendMode.MULTIPLY)
            } else {
                @Suppress("DEPRECATION")
                PorterDuffColorFilter(iconColor, PorterDuff.Mode.MULTIPLY)
            }
            binding.imgIcon.setImageDrawable(drawable)
        }
        binding.executePendingBindings()

        return binding.root
    }

    override fun getItemId(position: Int) = try {
        pageInfos[position].hashCode().toLong()
    } catch (_: IndexOutOfBoundsException) {
        0L
    }

    override fun getCount(): Int {
        return pageInfos.size
    }

    class TopPageViewHolder(val binding: ItemTopPageBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(pageInfos: List<PageInfo>) {
        this.pageInfos = pageInfos
        notifyDataSetChanged()
    }

    interface TopPagesListener {
        fun onItemClicked(pageInfo: PageInfo)
    }
}
