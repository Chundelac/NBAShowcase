package dev.venc.nbasample.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.venc.nbasample.repository.datamodel.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max

private const val STARTING_KEY = 1

class PlayersPagingSource(val repository: NBARepository) : PagingSource<Int, Player>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        val start = params.key ?: STARTING_KEY
        val range = start.until(start + params.loadSize)

        return try {
            val page = LoadResult.Page(
                withContext(Dispatchers.IO) {
                    repository.getPayers(start, params.loadSize)
                },

                // Make sure we don't try to load items behind the STARTING_KEY
                prevKey = when (start) {
                    STARTING_KEY -> null
                    else -> ensureValidKey(key = range.first - params.loadSize)
                },
                nextKey = range.last + 1
            )
            return page
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val player = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = player.playerId - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}