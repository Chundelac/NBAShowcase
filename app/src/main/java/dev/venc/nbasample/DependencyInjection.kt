package dev.venc.nbasample

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import dev.venc.nbasample.repository.NBARepository
import dev.venc.nbasample.ui.fragment.PlayerDetailViewModel
import dev.venc.nbasample.ui.fragment.PlayerListViewModel
import dev.venc.nbasample.ui.fragment.TeamDetailViewModel

object DependencyInjection {
    private fun provideNBARepository(): NBARepository = NBARepository()
    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideNBARepository())
    }
}

class ViewModelFactory(
    owner: SavedStateRegistryOwner, private val repository: NBARepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(PlayerListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return PlayerListViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(PlayerDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return PlayerDetailViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(TeamDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return TeamDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}