package com.ntrllog.bagels;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private int numDigits = 0;
    private char[] randomNumber;
    private int numOfGuesses = 1; // determine which question player is on
    private TextView guessTextView;
    private TextView hintTextView;
    private boolean gameDone = false;
    private String digitsEntered = ""; // number user has entered so far for each question

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        this.numDigits = getIntent().getIntExtra("NUM_DIGITS", 3);

        /* set activity title */
        switch (this.numDigits) {
            case 3:
                getSupportActionBar().setTitle(R.string.easy);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.easyButton)));
                break;
            case 4:
                getSupportActionBar().setTitle(R.string.medium);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.mediumButton)));
                break;
            case 5:
                getSupportActionBar().setTitle(R.string.hard);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.hardButton)));
                break;
            case 6:
                getSupportActionBar().setTitle(R.string.impossible);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.impossibleButton)));
                break;
        }

        /* display instructions */
        TextView instructionsTextView = findViewById(R.id.instructions);
        String instructions = getString(R.string.instructions, this.numDigits);
        instructionsTextView.setText(instructions);

        generateRandomNumber();
    }

    private void generateRandomNumber() {
        this.randomNumber = new char[this.numDigits];
        char[] array = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] shuffled = shuffleArray(array);
        System.arraycopy(shuffled, 0, this.randomNumber, 0, this.randomNumber.length);
    }

    /* https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array */
    private char[] shuffleArray(char[] array) {
        Random random = new Random();
        int index;
        char temp;
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public void onClick(View view) {
        getTextViews();

        if (this.gameDone) {
            return;
        }

        String button = ""+view.getTag();
        /* check button pressed */
        if (button.equals("check")) {
            /* not enough numbers entered */
            if (this.digitsEntered.length() < this.numDigits) {
                Toast.makeText(this, "Too few numbers entered", Toast.LENGTH_SHORT).show();
            }
            /* correct guess */
            else if (this.digitsEntered.equals(new String(this.randomNumber))) {
                this.hintTextView.setText(getResources().getString(R.string.correct));
                Toast.makeText(this, "You won!", Toast.LENGTH_LONG).show();
                this.gameDone = true;
            }
            /* wrong guess */
            else {
                this.hintTextView.setText(getHints());
                this.digitsEntered = "";

                if (this.numOfGuesses == 10) {
                    TextView actualNumberTextView = findViewById(R.id.actual_number);
                    String actualNumber = getString(R.string.actual_number, new String(this.randomNumber));
                    actualNumberTextView.setText(actualNumber);
                    actualNumberTextView.setTypeface(null, Typeface.BOLD);
                    this.gameDone = true;
                }
                else {
                    this.numOfGuesses++;
                }
            }
        }
        /* delete button pressed */
        else if (button.equals("delete")) {
            if (this.digitsEntered.length() > 0) {
                this.digitsEntered = this.digitsEntered.substring(0, this.digitsEntered.length()-1);
                String displayMessage = "" + this.numOfGuesses + ") " + this.digitsEntered;
                this.guessTextView.setText(displayMessage);
            }
        }
        /* number button pressed */
        else {
            if (this.digitsEntered.length() == this.numDigits) {
                Toast.makeText (this, "Too many numbers entered", Toast.LENGTH_SHORT).show();
            }
            else {
                this.digitsEntered += button;
                String displayMessage = "" + this.numOfGuesses + ") " + this.digitsEntered;
                this.guessTextView.setText(displayMessage);
            }
        }
    }

    private void getTextViews() {
        int[] guessIds = {0, R.id.guess_one, R.id.guess_two, R.id.guess_three, R.id.guess_four, R.id.guess_five, R.id.guess_six, R.id.guess_seven, R.id.guess_eight, R.id.guess_nine, R.id.guess_ten};
        int[] hintIds = {0, R.id.hint_one, R.id.hint_two, R.id.hint_three, R.id.hint_four, R.id.hint_five, R.id.hint_six, R.id.hint_seven, R.id.hint_eight, R.id.hint_nine, R.id.hint_ten};

        this.guessTextView = findViewById(guessIds[this.numOfGuesses]);
        this.hintTextView = findViewById(hintIds[this.numOfGuesses]);
    }

    private String getHints() {
        StringBuilder hintsStringBuilder = new StringBuilder();
        char[] enteredDigits = this.digitsEntered.toCharArray();

        for (int i = 0; i < enteredDigits.length; i++) {
            for (int j = 0; j < this.randomNumber.length; j++) {
                if (enteredDigits[i] == this.randomNumber[j]) {
                    if (i == j) {
                        hintsStringBuilder.append('F');
                    }
                    else {
                        hintsStringBuilder.append('P');
                    }
                }
            }
        }

        if (hintsStringBuilder.length() == 0) {
            hintsStringBuilder.append('B');
            return hintsStringBuilder.toString();
        }

        char[] hintsArray = new char[hintsStringBuilder.length()];
        for (int i = 0; i < hintsStringBuilder.length(); i++) {
            hintsArray[i] = hintsStringBuilder.charAt(i);
        }

        return new String(shuffleArray(hintsArray));
    }
}
