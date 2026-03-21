package com.myAllVideoBrowser.ui.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myAllVideoBrowser.R
import com.myAllVideoBrowser.data.local.model.Proxy
import com.myAllVideoBrowser.databinding.ItemProxiesBinding

interface ProxiesListener {
    fun onProxyRemoveClicked(proxy: Proxy)
    fun onProxyToggle(isChecked: Boolean)
}

class ProxiesAdapter(
    private val proxiesListener: ProxiesListener,
) : ListAdapter<Proxy, ProxiesAdapter.ProxiesViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Proxy>() {
            override fun areItemsTheSame(oldItem: Proxy, newItem: Proxy) =
                oldItem.host == newItem.host && oldItem.port == newItem.port

            override fun areContentsTheSame(oldItem: Proxy, newItem: Proxy) =
                oldItem == newItem
        }
    }

    class ProxiesViewHolder(val binding: ItemProxiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(proxy: Proxy, proxiesListener: ProxiesListener) {
            with(binding) {
                this.proxy = proxy
                this.proxiesListener = proxiesListener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProxiesViewHolder {
        val binding = DataBindingUtil.inflate<ItemProxiesBinding>(
            LayoutInflater.from(parent.context), R.layout.item_proxies, parent, false
        )
        return ProxiesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProxiesViewHolder, position: Int) {
        holder.bind(getItem(position), proxiesListener)
    }

    fun setData(proxies: List<Proxy>) {
        submitList(proxies.filter { it != Proxy.noProxy() })
    }
}
