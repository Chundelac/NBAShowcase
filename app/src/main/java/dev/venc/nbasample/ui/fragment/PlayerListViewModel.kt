package dev.venc.nbasample.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.venc.nbasample.repository.NBARepository
import dev.venc.nbasample.repository.datamodel.Player
import kotlinx.coroutines.flow.Flow

private const val ITEMS_PER_PAGE = 35
class PlayerListViewModel(private val nbaRepository: NBARepository) : ViewModel() {

    val items: Flow<PagingData<Player>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = true),
        pagingSourceFactory = { nbaRepository.playersPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)
}