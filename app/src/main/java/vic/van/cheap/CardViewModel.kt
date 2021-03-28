package vic.van.cheap

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModel

class CardViewModel(context: Context): ViewModel() {
    var amount: String = 0.0.toString()
    var weight: String = 1.0.toString()
    var result: Double? = null
    var backgroundResult: Drawable? = ResourcesCompat.getDrawable(context.resources,
        R.drawable.ic_result_normal, context.theme)

    constructor(cardModel: CardModel, context: Context) : this(context = context) {
        amount = "${cardModel.amount} \u20BD"
        weight = "${cardModel.weight} гр"
        result = cardModel.result
    }
    interface OnCardListener {
        fun deleteCard(position: Int)
    }
}