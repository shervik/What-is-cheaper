package vic.van.cheap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vic.van.cheap.databinding.BtnAddBinding
import vic.van.cheap.databinding.CardBinding

class ViewHolderFactory {

    class CardHolder(private var cardBinding: CardBinding) :
        RecyclerView.ViewHolder(cardBinding.root) {
        fun bind(cardViewModel: CardViewModel) {
            this.cardBinding.viewModel = cardViewModel
        }

        fun getCardBinding(): CardBinding {
            return cardBinding
        }
    }

    class BtnAddHolder(private var btnAddBinding: BtnAddBinding) : RecyclerView.ViewHolder(btnAddBinding.root) {

        fun getBtnAddBinding(): BtnAddBinding {
            return btnAddBinding
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                TYPE_FOOTER -> {
                    val inflater = LayoutInflater.from(parent.context)
                    val btnAddBinding: BtnAddBinding = BtnAddBinding.inflate(inflater, parent, false)
                    BtnAddHolder(btnAddBinding)
                }
                TYPE_ITEM -> {
                    val inflater = LayoutInflater.from(parent.context)
                    val cardBinding: CardBinding = CardBinding.inflate(inflater, parent, false)
                    CardHolder(cardBinding)
                }
                else -> null!!
            }
        }
    }

}