package com.ntrllog.bagels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button easyButton = findViewById(R.id.easy_button);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("NUM_DIGITS", 3);
                startActivity(intent);
            }
        });

        Button mediumButton = findViewById(R.id.medium_button);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("NUM_DIGITS", 4);
                startActivity(intent);
            }
        });

        Button hardButton = findViewById(R.id.hard_button);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("NUM_DIGITS", 5);
                startActivity(intent);
            }
        });

        Button impossibleButton = findViewById(R.id.impossible_button);
        impossibleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("NUM_DIGITS", 6);
                startActivity(intent);
            }
        });
    }
}
