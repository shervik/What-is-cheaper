package vic.van.cheap

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

const val TYPE_ITEM = 0
const val TYPE_FOOTER = 1

class CardAdapter(
    private val context: Context,
    var cards: MutableList<CardViewModel>,
    private var listener: CardViewModel.OnCardListener,
    var listenerBtn: BtnAddCardView.OnBtnAddListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var pos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ViewHolderFactory.CardHolder -> {
                holder.bind(cards[position])

                setBackgroundCard(holder, position)

                holder.getCardBinding().btnDelete.setOnClickListener {
                    pos = if (position < cards.size) position else cards.size - 1
                    cards.removeAt(pos)
                    notifyItemRemoved(pos)
                    listener.deleteCard(pos)
                }
            }

            is ViewHolderFactory.BtnAddHolder -> {
                holder.getBtnAddBinding().btnAddCard.setOnClickListener {
                    listenerBtn.addCard()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cards.count() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < cards.size -> {
                TYPE_ITEM
            }
            else -> TYPE_FOOTER
        }
    }

    private fun getDrawableResult(holder: ViewHolderFactory.CardHolder, drawableInt: Int) {
        holder.getCardBinding().viewModel!!.backgroundResult =
            ResourcesCompat.getDrawable(
                context.resources,
                drawableInt,
                context.theme
            )
    }

    private fun setBackgroundCard(holder: ViewHolderFactory.CardHolder, position: Int) {
        val minByOrNull = cards.minByOrNull { it.result!! }!!

        if (minByOrNull.result!! >= cards[position].result!!) getDrawableResult(holder,
            R.drawable.ic_result_good
        )
        else getDrawableResult(holder, R.drawable.ic_result_bad)
    }
}




