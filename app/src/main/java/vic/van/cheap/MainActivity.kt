package vic.van.cheap

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vic.van.cheap.BtnAddCardView.*
import vic.van.cheap.CardViewModel.*


class MainActivity : AppCompatActivity() {
    private lateinit var cardRV: RecyclerView
    private lateinit var cardViewModel: CardViewModel
    private var cards = mutableListOf<CardViewModel>()
    private lateinit var cardAdapter: CardAdapter
    private lateinit var mDialog: Dialog
    private lateinit var vibrator: Vibrator
    private lateinit var animation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        animation = AnimationUtils.loadAnimation(this, R.anim.shake)

        cardRV = findViewById(R.id.cardRV)
        cardRV.layoutManager = LinearLayoutManager(this)

        val onDeleteListener: OnCardListener = object : OnCardListener {
            override fun deleteCard(position: Int) {
                cardAdapter.notifyDataSetChanged()
            }
        }

        val onBtnAddListener: OnBtnAddListener = object : OnBtnAddListener {
            override fun addCard() {
                showAddDialog()
            }
        }

        cardAdapter = CardAdapter(
            this,
            cards,
            listener = onDeleteListener,
            listenerBtn = onBtnAddListener
        )
        cardRV.adapter = cardAdapter
    }

    private fun showAddDialog() {
        mDialog = Dialog(this)
        mDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mDialog.setContentView(R.layout.fragment_dialog_add)
        mDialog.show()

        val btnAddCard = mDialog.findViewById<ImageButton>(R.id.btnAdd)

        btnAddCard.setOnClickListener { updateData() }

    }

    private fun updateData() {
        val txtCount = mDialog.findViewById<EditText>(R.id.editCount)
        val txtWeight = mDialog.findViewById<EditText>(R.id.editWeight)

        if (isValidData(txtCount) && isValidData(txtWeight)) {
            val cardModel = CardModel(amount = txtCount.text, weight = txtWeight.text)
            cardViewModel = CardViewModel(cardModel, this)
            cardViewModel.result = cardModel.result
            cards.add(cardViewModel)

            cardAdapter.notifyDataSetChanged()
            mDialog.dismiss()
        }
    }

    private fun isValidData(editText: EditText): Boolean {
        return if (editText.text?.isEmpty() == true || editText.text.toString().toDouble() == 0.0) {

            animation.setAnimationListener(object : Animation.AnimationListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onAnimationStart(animation: Animation?) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            200, VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )

                    editText.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_background_error_cell,
                        theme
                    )
                }

                override fun onAnimationEnd(animation: Animation?) {
                    vibrator.cancel()

                    editText.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_background_edit_cell,
                        theme
                    )
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            editText.startAnimation(animation)
            false
        } else true
    }
}