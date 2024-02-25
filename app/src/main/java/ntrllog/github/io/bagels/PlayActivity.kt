package ntrllog.github.io.bagels

import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class PlayActivity : AppCompatActivity() {
    private var numDigits = 0
    private var randomNumberList: List<Char> = listOf()
    private var numOfGuesses = 1 // which question the user is on
    private var guessTextView: TextView? = null
    private var hintTextView: TextView? = null
    private var gameDone = false
    private var digitsEntered = "" // number that the user has entered so far for each question
    private var originalIntent: Intent? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)
        numDigits = intent.getIntExtra("NUM_DIGITS", 3)
        when (numDigits) {
            3 -> {
                supportActionBar!!.setTitle(R.string.easy)
                supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.easyButton)))
            }

            4 -> {
                supportActionBar!!.setTitle(R.string.medium)
                supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.mediumButton)))
            }

            5 -> {
                supportActionBar!!.setTitle(R.string.hard)
                supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.hardButton)))
            }

            6 -> {
                supportActionBar!!.setTitle(R.string.impossible)
                supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.impossibleButton)))
            }
        }

        /* display instructions */
        val instructionsTextView = findViewById<TextView>(R.id.instructions)
        val instructions = getString(R.string.instructions, numDigits)
        instructionsTextView.text = instructions
        randomNumberList = generateRandomNumber()

        originalIntent = intent
    }

    private fun generateRandomNumber(): List<Char> {
        val digits = ('0'..'9').toList().shuffled()
        return digits.take(numDigits)
    }

    fun onClick(view: View) {
        textViews
        if (gameDone) {
            return
        }

        val button = "" + view.tag
        if (button == "check") {
            if (digitsEntered.length < numDigits) {
                Toast.makeText(this, "Too few numbers entered", Toast.LENGTH_SHORT).show()
            } else if (digitsEntered == randomNumberList.joinToString("")) {
                hintTextView!!.text = resources.getString(R.string.correct)
                Toast.makeText(this, "You won!", Toast.LENGTH_LONG).show()
                gameDone = true
                val retryButton = findViewById<Button>(R.id.retry_button)
                retryButton.visibility = View.VISIBLE
            } else {
                hintTextView!!.text = hints
                digitsEntered = ""
                if (numOfGuesses == 10) {
                    val actualNumberTextView = findViewById<TextView>(R.id.actual_number)
                    val actualNumber = getString(
                        R.string.actual_number, randomNumberList.joinToString("")
                    )
                    actualNumberTextView.text = actualNumber
                    actualNumberTextView.setTypeface(null, Typeface.BOLD)
                    gameDone = true
                    val retryButton = findViewById<Button>(R.id.retry_button)
                    retryButton.visibility = View.VISIBLE
                } else {
                    ++numOfGuesses
                }
            }
        } else if (button == "delete") {
            if (digitsEntered.length > 0) {
                digitsEntered = digitsEntered.substring(0, digitsEntered.length - 1)
                val displayMessage = "$numOfGuesses) $digitsEntered"
                guessTextView!!.text = displayMessage
            }
        } else {
            if (digitsEntered.length == numDigits) {
                // too many numbers entered, don't do anything
            } else {
                digitsEntered += button
                val displayMessage = "$numOfGuesses) $digitsEntered"
                guessTextView!!.text = displayMessage
            }
        }
    }

    fun restartGame(view: View) {
        finish()
        startActivity(originalIntent)
    }

    private val textViews: Unit
        get() {
            val guessIds = intArrayOf(
                0,
                R.id.guess_one,
                R.id.guess_two,
                R.id.guess_three,
                R.id.guess_four,
                R.id.guess_five,
                R.id.guess_six,
                R.id.guess_seven,
                R.id.guess_eight,
                R.id.guess_nine,
                R.id.guess_ten
            )
            val hintIds = intArrayOf(
                0,
                R.id.hint_one,
                R.id.hint_two,
                R.id.hint_three,
                R.id.hint_four,
                R.id.hint_five,
                R.id.hint_six,
                R.id.hint_seven,
                R.id.hint_eight,
                R.id.hint_nine,
                R.id.hint_ten
            )
            guessTextView = findViewById(guessIds[numOfGuesses])
            hintTextView = findViewById(hintIds[numOfGuesses])
        }

    private val hints: String
        get() {
            val hintsList: MutableList<Char> = mutableListOf()
            val enteredDigits = digitsEntered.toCharArray()
            for (i in enteredDigits.indices) {
                for (j in randomNumberList.indices) {
                    if (enteredDigits[i] == randomNumberList[j]) {
                        if (i == j) {
                            hintsList.add('F')
                        } else {
                            hintsList.add('P')
                        }
                    }
                }
            }
            if (hintsList.isEmpty()) {
                hintsList.add('B')
            } else {
                hintsList.shuffle()
            }
            return hintsList.joinToString("")
        }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val dialogFragment: DialogFragment = ExitDialogFragment(
            applicationContext
        )
        dialogFragment.show(supportFragmentManager, "exit")
    }
}
