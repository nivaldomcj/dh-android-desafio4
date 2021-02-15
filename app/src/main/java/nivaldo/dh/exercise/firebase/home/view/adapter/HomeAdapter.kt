package nivaldo.dh.exercise.firebase.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nivaldo.dh.exercise.firebase.R
import nivaldo.dh.exercise.firebase.databinding.ItemGameBinding
import nivaldo.dh.exercise.firebase.home.model.Game

class HomeAdapter(
    private val gamesList: List<Game>,
    private val onGameClicked: (Game) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(gamesList[position], onGameClicked)
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    class HomeViewHolder(
        private val binding: ItemGameBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game, onGameClicked: (Game) -> Unit): Unit = with(binding) {
            tvGameTitle.text = game.title
            tvGameReleaseYear.text = game.releaseYear.toString()

            Glide.with(itemView.context)
                .load(game.imageStorageUrl)
                .centerCrop()
                .placeholder(R.drawable.layer_img_loading)
                .into(ivGameImage)

            itemView.setOnClickListener {
                onGameClicked(game)
            }
        }

    }


}