package com.ntrllog.bagels;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private int numDigits = 0;
    private char[] randomNumber;

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

        TextView actualNumberTextView = findViewById(R.id.actual_number);
        String actualNumber = getString(R.string.actual_number, new String(this.randomNumber));
        actualNumberTextView.setText(actualNumber);
        actualNumberTextView.setTypeface(null, Typeface.BOLD);
    }

    private void generateRandomNumber() {
        this.randomNumber = new char[this.numDigits];
        char[] array = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] shuffled = shuffleArray(array);
        for (int i = 0; i < this.numDigits; i++) {
            this.randomNumber[i] = shuffled[i];
        }
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
}
