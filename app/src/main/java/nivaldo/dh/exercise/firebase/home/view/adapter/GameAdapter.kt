package nivaldo.dh.exercise.firebase.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nivaldo.dh.exercise.firebase.databinding.ItemGameBinding
import nivaldo.dh.exercise.firebase.home.model.GameModel

class GameAdapter(
    private val gamesList: List<GameModel>,
    private val onGameClicked: (GameModel) -> Unit
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

        fun bind(gameModel: GameModel, onGameClicked: (GameModel) -> Unit): Unit = with(binding) {
            tvGameTitle.text = gameModel.title
            tvGameReleaseYear.text = gameModel.releaseYear.toString()

            // TODO chamar glide com a imagem vinda do storage

            itemView.setOnClickListener {
                onGameClicked(gameModel)
            }
        }

    }


}