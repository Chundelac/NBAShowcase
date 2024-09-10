package dev.venc.nbasample.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.venc.nbasample.repository.NBARepository
import dev.venc.nbasample.repository.datamodel.Player
import dev.venc.nbasample.repository.datamodel.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamDetailViewModel(private val nbaRepository: NBARepository) : ViewModel() {
    val teamDetail: MutableLiveData<Team> by lazy { MutableLiveData<Team>() }

    suspend fun loadTeamDetail(playerId: Int) = withContext(Dispatchers.IO) {
        teamDetail.postValue(nbaRepository.getPlayer(playerId).team)
    }
}