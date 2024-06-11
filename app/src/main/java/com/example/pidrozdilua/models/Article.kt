package com.example.pidrozdilua.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val description: String, // Assuming this is the first paragraph
    val thumbnailResId: Int
) : Parcelable
