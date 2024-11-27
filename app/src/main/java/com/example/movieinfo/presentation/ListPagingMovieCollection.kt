package com.example.movieinfo.presentation

import android.provider.UserDictionary.Words
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.entity.MovieBaseInfo
import com.example.movieinfo.entity.MovieCollection

class ListPagingMovieCollection(private val viewModel: MainViewModel,private val collectionType: CollectionType) : PagingSource<Int,MovieCollection>(){

    override fun getRefreshKey(state: PagingState<Int, MovieCollection>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieCollection> =
        kotlin.runCatching {
            viewModel.loadCollectionPaging(params.key?:1, collectionType = collectionType)
        }.fold(
            onSuccess = { list->
                LoadResult.Page(
                    data = list,
                    prevKey = params.key?.let { it-1 },
                    nextKey = (params.key?:1) + 1
                )
            },
            onFailure = {throwable->LoadResult.Error(throwable)}
        )
    companion object{
        fun pager(viewModel : MainViewModel,collectionType: CollectionType) = Pager(config = PagingConfig(pageSize = 10)
            , pagingSourceFactory = {ListPagingMovieCollection(viewModel, collectionType = collectionType)})
    }
}

class ListPagingFilterMovieCollection(private val viewModel: MainViewModel) : PagingSource<Int,MovieCollection>(){

    override fun getRefreshKey(state: PagingState<Int, MovieCollection>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieCollection> =
        kotlin.runCatching {
            viewModel.searchByFilter(params.key?:1)
        }.fold(
            onSuccess = { list->
                LoadResult.Page(
                    data = list,
                    prevKey = params.key?.let { it-1 },
                    nextKey = (params.key?:1) + 1
                )
            },
            onFailure = {throwable->LoadResult.Error(throwable)}
        )
    companion object{
        fun pager(viewModel : MainViewModel) = Pager(config = PagingConfig(pageSize = 10)
            , pagingSourceFactory = {ListPagingFilterMovieCollection(viewModel)})
    }
}
class ListPagingMovieBaseInfo(private val viewModel: MainViewModel,private val query: String) : PagingSource<Int,MovieBaseInfo>() {
    override fun getRefreshKey(state: PagingState<Int, MovieBaseInfo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieBaseInfo> =
        kotlin.runCatching {
            viewModel.searchByKeyWordPaging(query,params.key?:1)
        }.fold(
            onSuccess = { list->
                LoadResult.Page(
                    data = list,
                    prevKey = params.key?.let { it-1 },
                    nextKey = (params.key?:1) + 1
                )
            },
            onFailure = {throwable->LoadResult.Error(throwable)}
        )
//    companion object{
//        fun pager(viewModel : MainViewModel) =
//            Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory =
//            {ListPagingMovieBaseInfo(viewModel)})
//
//    }
}