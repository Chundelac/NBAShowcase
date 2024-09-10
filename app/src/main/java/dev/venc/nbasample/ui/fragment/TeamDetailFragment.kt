package dev.venc.nbasample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dev.venc.nbasample.DependencyInjection
import dev.venc.nbasample.databinding.FragmentTeamDetailBinding
import dev.venc.nbasample.repository.datamodel.Player
import dev.venc.nbasample.repository.datamodel.Team
import kotlinx.coroutines.launch

class TeamDetailFragment : Fragment() {
    private var _binding: FragmentTeamDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TeamDetailViewModel
    private val args: TeamDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModels<TeamDetailViewModel>(
            factoryProducer = { DependencyInjection.provideViewModelFactory(owner = this) }
        ).value
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val teamDetailObserver = Observer<Team> { newTeamDetail ->
            updateDetailView(newTeamDetail)
        }
        viewModel.teamDetail.observe(viewLifecycleOwner, teamDetailObserver)

        _binding = FragmentTeamDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadTeamDetail(args.playerId)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateDetailView(team: Team) {
        binding.nameText.text = team.name
        binding.fullNameText.text = team.fullName
        binding.abbreviationText.text = team.abbreviation
        binding.conferenceText.text = team.conference
        binding.divisionText.text = team.division
        binding.cityText.text = team.city
    }
}