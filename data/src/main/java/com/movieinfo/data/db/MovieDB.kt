package com.movieinfo.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.movieinfo.data.repository.storage.models.MyCollections
import com.movieinfo.data.repository.storage.models.MyMovieDb
import com.movieinfo.domain.entity.MovieType
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object MovieDbContracts {
    const val TABLE_NAME = "movies"
    const val TABLE_NAME_COLLECTIONS = "collections"

    object Columns {

        const val KP_ID = "kpID"
        const val ID = "ID"
        const val IMDB_ID = "imdbId"
        const val NAME_RU = "nameRU"
        const val NAME_ENG = "nameENG"
        const val NAME_OR = "nameOriginal"
        const val COUNTRIES = "countries"
        const val GENRES = "genreDtos"
        const val RAT_KP = "raitingKP"
        const val RAT_IMDB = "raitingImdb"
        const val YEAR = "year"
        const val TYPE = "type"
        const val POSTER_URL = "posterUrl"
        const val PREV_POSTER = "prevPosterUrl"
        const val COLLECTION_NAME = "collectionName"
        const val COLLECTION_ID = "collectionID"
    }
}
class MovieTypeConverter {
    @TypeConverter
    fun convertMovieTypeToString(movieType: MovieType): String = movieType.name

    @TypeConverter
    fun convertStringToMovieType(string: String): MovieType = MovieType.valueOf(string)
    @TypeConverter
    fun arrayIntToString(list: List<Int>): String{
        val type = Types.newParameterizedType(
            List::class.java,
            Int::class.javaObjectType
        )
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<List<Int>>(type)
        return adapter.toJson(list)
    }
    @TypeConverter
    fun stringToArrayInt(string: String): List<Int>?{
        val type = Types.newParameterizedType(
            List::class.java,
            Int::class.javaObjectType
        )
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<List<Int>>(type)
        return adapter.fromJson(string)?.map { it }

    }

}
@Entity(tableName = MovieDbContracts.TABLE_NAME_COLLECTIONS,
   indices = [Index(value = [MovieDbContracts.Columns.COLLECTION_NAME], unique = true)] )
data class MyMovieCollectionsDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MovieDbContracts.Columns.ID)
    override val id: Int,
    @ColumnInfo(name = MovieDbContracts.Columns.COLLECTION_NAME)
    override val collectionName: String
) : MyCollections

@Entity(tableName = MovieDbContracts.TABLE_NAME)
@TypeConverters(MovieTypeConverter::class)
data class MovieCollectionDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = MovieDbContracts.Columns.KP_ID)
    override val kpID: Int,
//    @ColumnInfo(name = MovieDbContracts.Columns.IMDB_ID)

    override val imdbId: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.NAME_RU)

    override val nameRU: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.NAME_ENG)

    override val nameENG: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.NAME_OR)

    override val nameOriginal: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.COUNTRIES)

    override val countries: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.GENRES)

    override val genre: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.RAIT_KP)

    override val ratingKP: Float?,
//    @ColumnInfo(name = MovieDbContracts.Columns.RAIT_IMDB)

    override val ratingImdb: Float?,
//    @ColumnInfo(name = MovieDbContracts.Columns.YEAR)

    override val year: Int?,
//    @ColumnInfo(name = MovieDbContracts.Columns.TYPE)

    override val type: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.POSTER_URL)

    override val posterUrl: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.PREV_POSTER)

    override val prevPosterUrl: String?,
    @ColumnInfo(name = MovieDbContracts.Columns.COLLECTION_ID)
    override val collectionId: List<Int>
):MyMovieDb


data class MoviesByCollectionId(
    @Embedded
    val collectionName: MyMovieCollectionsDb,
    @Relation(
        parentColumn = MovieDbContracts.Columns.ID,
        entityColumn = MovieDbContracts.Columns.COLLECTION_ID
    )
    val movieCollectionDB: List<MovieCollectionDB>
)