package dev.venc.nbasample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.venc.nbasample.DependencyInjection
import dev.venc.nbasample.databinding.FragmentPlayerListBinding
import dev.venc.nbasample.repository.datamodel.Player
import dev.venc.nbasample.ui.adapter.PlayerGridAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlayerListFragment : Fragment() {

    private var _binding: FragmentPlayerListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlayerListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModels<PlayerListViewModel>(
            factoryProducer = { DependencyInjection.provideViewModelFactory(owner = this) }
        ).value
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPlayerListAdapter()
    }

    private fun setupPlayerListAdapter() {
        val items = viewModel.items
        val playerAdapter = PlayerGridAdapter()
        binding.playerRecyclerView.adapter = playerAdapter
        binding.playerRecyclerView.layoutManager = GridLayoutManager(binding.playerRecyclerView.context, 2)
        playerAdapter.onItemClick = { item ->
            listItemClicked(item)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    playerAdapter.submitData(it)
                }
            }
        }
    }

    private fun listItemClicked(player: Player) {
        findNavController().navigate(
            PlayerListFragmentDirections
                .actionPlayerListFragmentToPlayerDetailFragment(player.playerId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}