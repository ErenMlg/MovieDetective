package com.softcross.moviedetective.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softcross.moviedetective.data.dto.actors.ActorDto
import com.softcross.moviedetective.data.mapper.ActorResponseListMapper
import com.softcross.moviedetective.data.remote.ActorService
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Actor

class RemoteActorMediator(
    private val actorService: ActorService,
    private val actorMapper: MovieDetectiveListMapper<ActorDto, Actor> = ActorResponseListMapper()
) : PagingSource<Int, Actor>() {


    override fun getRefreshKey(state: PagingState<Int, Actor>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Actor> {
        return try {
            val page = params.key ?: 1
            val response = actorService.getPopularActors(page).actors
            val mappedResponse = actorMapper.map(response)

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