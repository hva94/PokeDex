package com.hvasoft.pokedex.presentation.ui.common

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import com.hvasoft.pokedex.R

fun Fragment.showPopUpMessage(anyMessage: Any, isError: Boolean = false) {
    val duration = if (isError) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
    val message = if (anyMessage is Int) getString(anyMessage) else anyMessage.toString()
    view?.let { rootView ->
        val snackBar = Snackbar.make(rootView, message, duration)
        val params = snackBar.view.layoutParams as ViewGroup.MarginLayoutParams
        val extraBottomMargin = resources.getDimensionPixelSize(R.dimen.dimen_64)
        params.setMargins(
            params.leftMargin,
            params.topMargin,
            params.rightMargin,
            params.bottomMargin + extraBottomMargin
        )
        snackBar.view.layoutParams = params
        snackBar.show()
    }
}

fun ImageView.loadImageWithUrl(
    url: String?,
    isCircle: Boolean = false,
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {}
) {
    var glideRequest = Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
    if (isCircle) glideRequest = glideRequest.circleCrop()
    glideRequest
        .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                setImageDrawable(resource)
                onSuccess()
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                onError()
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                onError()
            }
        })
}

fun String.isInputInitialValid(): Boolean {
    if (isEmpty())
        return false
    val words = split(" ")
    if (words.isNotEmpty()) {
        val firstWord = words.first()
        if (firstWord.isNotEmpty() && firstWord.first().isLetterOrDigit())
            return true
    }
    return false
}

fun String.getInitials(): String {
    val words = split(" ")
    if (words.isNotEmpty()) {
        val firstWord = words.first()
        if (firstWord.isNotEmpty() && firstWord.first().isLetterOrDigit())
            return firstWord.first().toString()
    }
    return ""
}