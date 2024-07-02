package com.softcross.moviedetective.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.mapper.MovieResponseListMapper
import com.softcross.moviedetective.data.remote.MovieService
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Content


class RemoteMovieMediator(
    private val requestType: RemoteMovieMediatorRequestType,
    private val movieService: MovieService,
    private val movieMapper: MovieDetectiveListMapper<MovieDto, Content> = MovieResponseListMapper(),
) : PagingSource<Int, Content>() {


    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val page = params.key ?: 1
            val response = when (requestType) {
                is RemoteMovieMediatorRequestType.POPULAR -> movieService.getPopularMovies(page).data
                is RemoteMovieMediatorRequestType.TREND -> movieService.getTrendMovies(page).data
                is RemoteMovieMediatorRequestType.COMING_SOON -> movieService.getComingSoonMovies(page).data
                is RemoteMovieMediatorRequestType.GENRE -> movieService.getMovieByGenre(requestType.genres, page).data
            }
            val mappedResponse = movieMapper.map(response)

            LoadResult.Page(
                data = mappedResponse,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (mappedResponse.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

sealed class RemoteMovieMediatorRequestType {
    data object POPULAR : RemoteMovieMediatorRequestType()
    data object TREND : RemoteMovieMediatorRequestType()
    data object COMING_SOON : RemoteMovieMediatorRequestType()
    data class GENRE(val genres: String) : RemoteMovieMediatorRequestType()
}