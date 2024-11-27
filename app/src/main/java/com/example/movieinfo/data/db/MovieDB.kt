package com.example.movieinfo.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.util.TableInfo
import com.example.movieinfo.entity.MovieType
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
        const val RAIT_KP = "raitingKP"
        const val RAIT_IMDB = "raitingImdb"
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
data class MyMovieCollections(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MovieDbContracts.Columns.ID)
    val id: Int,
    @ColumnInfo(name = MovieDbContracts.Columns.COLLECTION_NAME)
    val collectionName: String
)

@Entity(tableName = MovieDbContracts.TABLE_NAME)
@TypeConverters(MovieTypeConverter::class)
data class MovieCollectionDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = MovieDbContracts.Columns.KP_ID)
     val kpID: Int,
//    @ColumnInfo(name = MovieDbContracts.Columns.IMDB_ID)

     val imdbId: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.NAME_RU)

     val nameRU: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.NAME_ENG)

     val nameENG: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.NAME_OR)

     val nameOriginal: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.COUNTRIES)

     val countries: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.GENRES)

     val genreDtos: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.RAIT_KP)

     val raitingKP: Float?,
//    @ColumnInfo(name = MovieDbContracts.Columns.RAIT_IMDB)

     val raitingImdb: Float?,
//    @ColumnInfo(name = MovieDbContracts.Columns.YEAR)

     val year: Int?,
//    @ColumnInfo(name = MovieDbContracts.Columns.TYPE)

     val type: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.POSTER_URL)

     val posterUrl: String?,
//    @ColumnInfo(name = MovieDbContracts.Columns.PREV_POSTER)

     val prevPosterUrl: String?,
    @ColumnInfo(name = MovieDbContracts.Columns.COLLECTION_ID)
    val collectionID: List<Int>
)


data class MoviesByCollectionId(
    @Embedded
    val collectionName: MyMovieCollections,
    @Relation(
        parentColumn = MovieDbContracts.Columns.ID,
        entityColumn = MovieDbContracts.Columns.COLLECTION_ID
    )
    val movieCollectionDB: List<MovieCollectionDB>
)