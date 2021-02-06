package nivaldo.dh.exercise.firebase.home.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.ItemGameBinding
import nivaldo.dh.exercise.firebase.home.model.Game

class GameAdapter(
    private val gamesList: List<Game>,
    private val onGameClicked: (Game) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gamesList[position], onGameClicked)
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    class GameViewHolder(
        private val binding: ItemGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game, onGameClicked: (Game) -> Unit): Unit = with(binding) {
            tvGameTitle.text = game.title
            tvGameReleaseYear.text = game.releaseYear.toString()

            Glide.with(itemView.context)
                .load(game.imageStoragePath)
                .centerCrop()
                .placeholder(R.drawable.layer_img_loading)
                .into(ivGameImage)

            itemView.setOnClickListener {
                onGameClicked(game)
            }
        }

    }


}