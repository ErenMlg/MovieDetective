package com.softcross.moviedetective.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.mapper.MovieResponseListMapper
import com.softcross.moviedetective.data.remote.MovieService
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Movie
import javax.inject.Inject


class RemoteMovieMediator(
    private val requestType: RequestType,
    private val movieService: MovieService,
    private val movieMapper: MovieDetectiveListMapper<MovieDto, Movie> = MovieResponseListMapper(),
) : PagingSource<Int, Movie>() {


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = when (requestType) {
                is RequestType.POPULAR -> movieService.getPopularMovies(page).data
                is RequestType.TREND -> movieService.getTrendMovies(page).data
                is RequestType.COMING_SOON -> movieService.getComingSoonMovies(page).data
                is RequestType.GENRE -> movieService.getMovieByGenre(requestType.genres, page).data
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

sealed class RequestType {
    data object POPULAR : RequestType()
    data object TREND : RequestType()
    data object COMING_SOON : RequestType()
    data class GENRE(val genres: String) : RequestType()
}