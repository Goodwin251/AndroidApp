package com.example.pidrozdilua.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link(
    val title: String,
    val url: String,
    var imageUrl: String? = null // Add this property
) : Parcelable
