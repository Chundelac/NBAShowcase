package dev.venc.nbasample.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.venc.nbasample.repository.NBARepository
import dev.venc.nbasample.repository.datamodel.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerDetailViewModel(private val nbaRepository: NBARepository) : ViewModel() {
    val playerDetail: MutableLiveData<Player> by lazy { MutableLiveData<Player>() }

    suspend fun loadPlayerDetail(id: Int) = withContext(Dispatchers.IO) {
        playerDetail.postValue(nbaRepository.getPlayer(id))
    }
}