package com.myAllVideoBrowser.ui.component.binding

import androidx.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.net.toUri
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageBinding {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context).load(url).into(view)
    }

    @BindingAdapter("bitmap")
    @JvmStatic
    fun setBitmap(view: ImageView, bitmap: Bitmap?) {
        bitmap?.let { view.setImageBitmap(it) }
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageUri(view: ImageView, imageUri: String?) {
        if (imageUri == null) {
            view.setImageURI(null)
        } else {
            view.setImageURI(imageUri?.toUri())
        }
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageUri(view: ImageView, imageUri: Uri?) {
        view.setImageURI(imageUri)
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageDrawable(view: ImageView, drawable: Drawable?) {
        view.setImageDrawable(drawable)
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }
}
