package com.example.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {

    EditText reg_name;
    Button reg_btn;
    String reg_guest , guest_name;
    int min = 10000;
    int max = 99999;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY = "myKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reg_btn = findViewById(R.id.reg_btn);
        reg_name = findViewById(R.id.ED_userName);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_guest = reg_name.getText().toString();

                Random r = new Random();
                int i1 = r.nextInt(max - min + 1) + min;

                guest_name = reg_guest + "#" + String.valueOf(i1);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY, guest_name);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}