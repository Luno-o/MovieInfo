package com.example.movieinfo.data.moviesDto

import com.example.movieinfo.entity.MovieForStaff
import com.example.movieinfo.entity.Spouses
import com.example.movieinfo.entity.StaffFullInfo

data class StaffFullInfoImpl(
    override val personId: Int,
    override val webUrl: String?,
    override val nameRU: String?,
    override val nameEN: String?,
    override val sex: String?,
    override val posterUrl: String?,
    override val growth: Int,
    override val birthday: String?,
    override val death: String?,
    override val age: Int,
    override val birthPlace: String?,
    override val deathPlace: String?,
    override val hasAwards: Int?,
    override val profession: String?,
    override val facts: List<String?>,
    override val spouses: List<Spouses>,
    override val films: List<MovieForStaff>
) : StaffFullInfo

