package dev.venc.nbasample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dev.venc.nbasample.DependencyInjection
import dev.venc.nbasample.databinding.FragmentPlayerDetailBinding
import dev.venc.nbasample.repository.datamodel.Player
import kotlinx.coroutines.launch

class PlayerDetailFragment : Fragment() {
    private var _binding: FragmentPlayerDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlayerDetailViewModel
    private val args: PlayerDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModels<PlayerDetailViewModel>(
            factoryProducer = { DependencyInjection.provideViewModelFactory(owner = this) }
        ).value
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val playerDetailObserver = Observer<Player> { newPlayerDetail ->
            updateDetailView(newPlayerDetail)
        }
        viewModel.playerDetail.observe(viewLifecycleOwner, playerDetailObserver)

        _binding = FragmentPlayerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadPlayerDetail(args.playerId)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateDetailView(player: Player) {
        binding.firstNameText.text = player.firstName
        binding.lastNameText.text = player.lastName
        binding.positionText.text = player.position
        binding.heightText.text = player.height
        binding.weightText.text = player.weight
        binding.jerseyNumberText.text = player.jerseyNumber
        binding.collegeText.text = player.college
        binding.countryText.text = player.country
        binding.draftYearText.text = player.draftYear.toString()
        binding.draftRoundText.text = player.draftRound.toString()
        binding.draftNumberText.text = player.draftNumber.toString()
        binding.teamInfoButton.setOnClickListener {
            findNavController().navigate(
                PlayerDetailFragmentDirections
                    .actionPlayerDetailFragmentToTeamDetailFragment(player.playerId)
            )
        }
    }
}