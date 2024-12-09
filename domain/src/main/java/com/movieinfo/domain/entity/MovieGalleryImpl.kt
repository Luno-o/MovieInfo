package com.movieinfo.domain.entity



data class MovieGalleryImpl(override val imageUrl: String,
                       override val previewUrl: String)
    : MovieGallery

