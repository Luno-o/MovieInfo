package com.movieinfo.domain.entity


data class YourCollection(val collectionName: String, val collection: List<MovieCollection>)

interface Country {
    val country: String
}

interface Episode {
    val seasonNumber: Int
    val episodeNumber: Int
    val nameRu: String?
    val nameEng: String?
    val synopsys: String?
    val releasedDate: String?
}

interface Genre {
    val  genre: String
}

interface  MovieBaseInfo{
    val kpID: Int
    val kpHdId: String?
    val ImdbId : String?
    val nameRU: String?
    val nameENG: String?
    val nameOriginal: String?
    val posterUrl: String?
    val prevPosterUrl: String?
    val coverUrl: String?
    val logoUrl: String?
    val reviewsCount: Int?
    val ratingGoodReview: Float?
    val ratingGoodReviewVoteCount: Int?
    val ratingKinopoisk: Float?
    val ratingKinopoiskVoteCount: Int?
    val ratingImdb: Float?
    val ratingImdbVoteCount: Int?
    val ratingFilmCritics: Float?
    val ratingFilmCriticsVoteCount: Int?
    val ratingAwait: Float?
    val ratingAwaitCount: Int?
    val ratingRfCritics: Float?
    val ratingRfCriticsVoteCount: Int?
    val webUrl: String?
    val year: Int?
    val filmLength: Int?
    val slogan: String?
    val description: String?
    val shortDescription: String?
    val editorAnnotation: String?
    val isTicketsAvailable: Boolean?
    val productionStatus: String?
    val type: String?
    val ratingMpaa: String?
    val ratingAgeLimits: String?
    val hasImax: Boolean?
    val has3D: Boolean?
    val lastSync: String?
    val countries: List<Country>
    val genres: List<Genre>
    val startYear: Int?
    val endYear: Int?
    val serial: Boolean?
    val shortFilm: Boolean?
    val completed: Boolean?
}
interface MyMovieCollections{
    val id: Int
    val collectionName: String
}


interface SerialWrapper{
    val number: Int
    val episodes: List<Episode>
}
interface MovieCollectionId{
    val collectionId: List<Int>
}
data class MovieDb(
    override val collectionId: List<Int>,
    override val kpID: Int,
    override val imdbId: String?,
    override val nameRU: String?,
    override val nameENG: String?,
    override val nameOriginal: String?,
    override val countries: List<Country>?,
    override val genre: List<Genre>?,
    override val ratingKP: Float?,
    override val ratingImdb: Float?,
    override val year: Int?,
    override val type: String?,
    override val posterUrl: String?,
    override val prevPosterUrl: String?
) : MovieCollectionId,MovieCollection

interface MovieForStaff {
    val filmId: Int
    val nameRU: String?
    val nameENG: String?
    val rating: String?
    val general: Boolean
    val description: String?
    val professionKey: String?
}

interface MovieGallery {
    val imageUrl: String
    val previewUrl: String
}

interface MoviePremiere {
    val kpID: Int
    val nameRU: String
    val nameENG: String
    val year: Int
    val posterUrl: String
    val prevPosterUrl: String
    val countries: List<Country>
    val genre: List<Genre>
    val duration: Int?
    val premiereRu: String
}

interface SimilarMovie {
    val filmID: Int
    val nameRU: String
    val nameENG: String?
    val nameOriginal: String?
    val posterUrl: String
    val prevPosterUrl: String
    val relation: String
}

interface Spouses {
    val personId: Int
    val name: String?
    val divorced: Boolean
    val divorcedReason: String
    val sex: String
    val children: Int
    val webUrl: String
    val relation: String
}

interface Staff {
    val staffId: Int
    val nameRU: String?
    val nameENG: String?
    val description: String?
    val posterUrl: String?
    val professionText: String?
    val professionKey: String?
}

interface StaffFullInfo {
    val personId: Int
    val webUrl: String?
    val nameRU: String?
    val nameEN: String?
    val sex: String?
    val posterUrl: String?
    val growth: Int
    val birthday: String?
    val death: String?
    val age: Int
    val birthPlace: String?
    val deathPlace: String?
    val hasAwards: Int?
    val profession: String?
    val facts: List<String?>
    val spouses: List<Spouses>
    val films: List<MovieForStaff>
}
interface SearchMovieFilter {
    val countryInd: Array<Int>
    val genreInd: Array<Int>
    val sortType: String
    val movieType: String
    val raitingFrom: Int
    val raitingTo: Int
    val yearBefore: Int
    val yearAfter: Int
    val queryState: String
}