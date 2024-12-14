package com.example.movieinfo.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieinfo.presentation.ui.viewModels.MainPageViewModel
import com.example.movieinfo.presentation.ui.viewModels.SearchPageViewModel
import com.example.movieinfo.presentation.ui.viewModels.ShowCollectionViewModel
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.SearchMovieFilter

class ListPagingMovieCollection(
    private val viewModel: ShowCollectionViewModel,
    private val collectionType: CollectionType,
    private val id: Int = 0
) : PagingSource<Int, MovieCollection>() {

    override fun getRefreshKey(state: PagingState<Int, MovieCollection>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieCollection> =
        kotlin.runCatching {
            when (collectionType) {
                CollectionType.SIMILAR -> {
                    viewModel.loadSimilarMovie(id)
                }
                else -> {
                    viewModel.loadCollectionPaging(params.key ?: 1, collectionType = collectionType)
                }
            }
        }.fold(
            onSuccess = { list ->
                LoadResult.Page(
                    data = list,
                    prevKey = if (list.size != params.loadSize) null else params.key?.let { it - 1 },
                    nextKey = if (list.size != params.loadSize) null else (params.key ?: 1) + 1
                )
            },
            onFailure = { throwable -> LoadResult.Error(throwable) }
        )

    companion object {
        fun pager(viewModel: ShowCollectionViewModel, collectionType: CollectionType, id: Int) =
            Pager(config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    ListPagingMovieCollection(
                        viewModel,
                        collectionType = collectionType,
                        id
                    )
                })
    }
}


class ListPagingFilterMovieCollection(private val viewModel: SearchPageViewModel) :
    PagingSource<Int, MovieCollection>() {

    override fun getRefreshKey(state: PagingState<Int, MovieCollection>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieCollection> {

        return kotlin.runCatching {
            viewModel.searchByFilter(params.key ?: 1)
        }.fold(
            onSuccess = { list ->
                LoadResult.Page(
                    data = list,
                    prevKey = if (list.size < params.loadSize) null else params.key?.let { it - 1 },
                    nextKey = if (list.size < params.loadSize) null else (params.key ?: 1) + 1
                )
            },
            onFailure = { throwable -> LoadResult.Error(throwable) }
        )
    }

    companion object {
        fun pager(viewModel: SearchPageViewModel) = Pager(config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { ListPagingFilterMovieCollection(viewModel) })
    }
}

class ListPagingMovieBaseInfo(
    private val viewModel: SearchPageViewModel,
    private val query: String
) : PagingSource<Int, MovieCollection>() {
    override fun getRefreshKey(state: PagingState<Int, MovieCollection>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieCollection> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), null, null)
        }
        return kotlin.runCatching {
            viewModel.searchByKeyWordPaging(query, params.key ?: 1)
        }.fold(
            onSuccess = { list ->
                LoadResult.Page(
                    data = list,
                    prevKey = params.key?.let { it - 1 },
                    nextKey = (params.key ?: 1) + 1
                )
            },
            onFailure = { throwable -> LoadResult.Error(throwable) }
        )
    }
}