package com.exceldroid.kidstvlauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class PinActivity extends AppCompatActivity {
    private EditText pinInput;
    private static final String CORRECT_PIN = "1234"; // Default PIN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        pinInput = findViewById(R.id.pinInput);
        Button submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(v -> {
            String enteredPin = pinInput.getText().toString();
            if (enteredPin.equals(CORRECT_PIN)) {
                // Return to system launcher
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Wrong PIN", Toast.LENGTH_SHORT).show();
                pinInput.setText("");
            }
        });
    }
}