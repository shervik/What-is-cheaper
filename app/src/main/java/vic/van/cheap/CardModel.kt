package vic.van.cheap

import android.text.Editable
import kotlin.math.ceil
import kotlin.math.pow

data class CardModel(
    var amount: Editable?,
    var weight: Editable?
) {
    val result: Double
        get() {
            val scale = 10.0.pow(1.0)
            return ceil(amount.toString().toDouble() / weight.toString().toDouble() * scale) / scale
        }
}
