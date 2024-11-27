package com.example.movieinfo.entity

import com.example.movieinfo.data.moviesDto.CountryDto
import com.example.movieinfo.data.moviesDto.GenreDto


enum class CollectionType{
    TOP_POPULAR_ALL,
    TOP_POPULAR_MOVIES,
    TOP_250_TV_SHOWS,
    TOP_250_MOVIES,
VAMPIRE_THEME,
    COMICS_THEME,
    CLOSES_RELEASES,
    FAMILY,
    OSKAR_WINNERS_2021,
    LOVE_THEME,
    ZOMBIE_THEME,
    CATASTROPHE_THEME,
    KIDS_ANIMATION_THEME,
    POPULAR_SERIES,
    PREMIERES,
    SIMILAR,
    BEST,WATCHED,
    INTEREST,
    MY_COLLECTIONS
}
enum class OrderType{
    YEAR,NUM_VOTE,RATING
}
enum class GalleryType {
    STILL,// кадры
    SHOOTING,//изображения со съемок
    POSTER,//постеры
    FAN_ART,
    PROMO,
    CONCEPT,
    WALLPAPER,//обои
    COVER,
    SCREENSHOT
}
interface MovieCollection {
    val kpID: Int
    val imdbId : String?
    val nameRU: String?
    val nameENG: String?
    val nameOriginal: String?
    val countries: List<CountryDto>?
    val genreDtos: List<GenreDto>?
    val raitingKP: Float?
    val raitingImdb: Float?
    val year: Int?
    val type: String?
    val posterUrl: String?
    val prevPosterUrl: String?
}
class MovieCollectionImp(
    override val kpID: Int,
    override val imdbId: String?,
    override val nameRU: String?,
    override val nameENG: String?,
    override val nameOriginal: String?,
    override val countries: List<CountryDto>?,
    override val genreDtos: List<GenreDto>?,
    override val raitingKP: Float?,
    override val raitingImdb: Float?,
    override val year: Int?,
    override val type: String?,
    override val posterUrl: String?,
    override val prevPosterUrl: String?
):MovieCollection

enum class MovieType {
    ALL,FILM,TV_SERIES, TV_SHOW, MINI_SERIES,
}
enum class ProfessionKey{
    WRITER, OPERATOR, EDITOR, COMPOSER, PRODUCER_USSR, TRANSLATOR, DIRECTOR, DESIGN, PRODUCER, ACTOR, VOICE_DIRECTOR, UNKNOWN,
    HERSELF
}