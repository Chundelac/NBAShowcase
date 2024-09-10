package dev.venc.nbasample.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.venc.nbasample.R
import dev.venc.nbasample.repository.datamodel.Player
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class PlayerGridAdapter :
    PagingDataAdapter<Player, PlayerGridAdapter.ViewHolder>(PLAYER_DIFF_CALLBACK) {
    var onItemClick: ((Player) -> Unit)? = null

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var loadingTask: kotlinx.coroutines.Deferred<Unit>? = null
        val nameTextView: TextView = view.findViewById(R.id.name)
        val background: ConstraintLayout = view.findViewById(R.id.background)
        val positionTextView: TextView = view.findViewById(R.id.position)
        val teamTextView: TextView = view.findViewById(R.id.team)
        private val contentGroup: Group = view.findViewById(R.id.contentGroup)

        suspend fun setLoading(loading: Boolean) = withContext(Dispatchers.Main) {
            contentGroup.visibility = if (loading) View.GONE else View.VISIBLE
            if (loading) background.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#AAAAAA")) // TODO: better loading state handling
        }
    }

    private lateinit var context: Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.player_grid_item, viewGroup, false)
        if (!this::context.isInitialized) {
            context = view.context
        }

        return ViewHolder(view)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                viewHolder.setLoading(true)

                getItem(position)?.let { item ->
                    viewHolder.nameTextView.text = "${item.firstName} ${item.lastName}"
                    viewHolder.teamTextView.text = item.team.name
                    viewHolder.positionTextView.text = item.position
                    viewHolder.view.setOnClickListener { onItemClick?.invoke(item) }
                }
            }

            viewHolder.setLoading(false)
        }

    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.loadingTask?.cancel()
        super.onViewRecycled(holder)
        runBlocking {
            holder.loadingTask?.join()
        }
    }

    companion object {
        private val PLAYER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Player>() {
            override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean =
                oldItem.playerId == newItem.playerId

            override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean =
                oldItem == newItem
        }
    }
}