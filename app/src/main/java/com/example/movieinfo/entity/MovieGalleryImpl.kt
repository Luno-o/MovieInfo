package com.example.movieinfo.entity

import android.os.Parcelable
import com.example.movieinfo.data.moviesDto.CountryDto
import com.example.movieinfo.data.moviesDto.GenreDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieGalleryImpl(override val imageUrl: String,
                       override val previewUrl: String)
    :MovieGallery ,Parcelable

