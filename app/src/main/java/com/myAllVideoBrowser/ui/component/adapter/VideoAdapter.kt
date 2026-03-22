package com.myAllVideoBrowser.ui.component.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.color.MaterialColors
import com.myAllVideoBrowser.R
import com.myAllVideoBrowser.data.local.model.LocalVideo
import com.myAllVideoBrowser.databinding.ItemVideoBinding
import com.myAllVideoBrowser.util.FileUtil

class VideoAdapter(
    private val videoListener: VideoListener,
    private val fileUtil: FileUtil
) : ListAdapter<LocalVideo, VideoAdapter.VideoViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LocalVideo>() {
            override fun areItemsTheSame(oldItem: LocalVideo, newItem: LocalVideo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: LocalVideo, newItem: LocalVideo) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = DataBindingUtil.inflate<ItemVideoBinding>(
            LayoutInflater.from(parent.context), R.layout.item_video, parent, false
        )
        return VideoViewHolder(binding, fileUtil)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) =
        holder.bind(getItem(position), videoListener)

    class VideoViewHolder(var binding: ItemVideoBinding, var fileUtil: FileUtil) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(localVideo: LocalVideo, videoListener: VideoListener) {
            val size = getScreenResolution(itemView.context)
            val color = MaterialColors.getColor(
                itemView.context,
                com.google.android.material.R.attr.colorSurfaceVariant,
                Color.YELLOW
            )

            with(binding) {
                this.localVideo = localVideo
                this.videoListener = videoListener
                this.cardVideo.setCardBackgroundColor(color)
                Glide.with(this@VideoViewHolder.itemView.context).load(localVideo.uri).fitCenter()
                    .error(R.drawable.ic_video_24dp)
                    .placeholder(R.drawable.ic_video_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .apply(RequestOptions().override(size.first / 8, size.second / 8))
                    .into(this.ivThumbnail)

                executePendingBindings()
            }
        }

        private fun getScreenResolution(context: Context): Pair<Int, Int> {
            val displayMetrics = context.resources.displayMetrics
            return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
        }
    }

    fun setData(localVideos: List<LocalVideo>) {
        submitList(localVideos)
    }
}

interface VideoListener {
    fun onItemClicked(localVideo: LocalVideo)
    fun onMenuClicked(view: View, localVideo: LocalVideo)
}

@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.ERROR)
    }
}
