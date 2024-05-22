package com.example.kisiler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class KisiekleActivity extends AppCompatActivity {
    EditText editTextNewName;
    EditText editTextNewPhoneNumber;
    Button buttonAdd;
    Veritabani veritabani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisiekle);


        editTextNewName = findViewById(R.id.editTextNewName);
        editTextNewPhoneNumber = findViewById(R.id.editTextNewPhoneNumber);
        buttonAdd = findViewById(R.id.buttonAdd);
        veritabani = new Veritabani(this);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newName = editTextNewName.getText().toString().trim();
                String newPhoneNumber = editTextNewPhoneNumber.getText().toString().trim();


                if (!newName.isEmpty() && !newPhoneNumber.isEmpty()) {

                    long result = veritabani.addContact(newName, newPhoneNumber);
                    if (result != -1) {

                        Toast.makeText(KisiekleActivity.this, "Kişi rehbere eklendi..", Toast.LENGTH_SHORT).show();


                        editTextNewName.setText("");
                        editTextNewPhoneNumber.setText("");
                        Intent intent;
                        intent = new Intent(KisiekleActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(KisiekleActivity.this, "Kişi Eklenemedi..", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(KisiekleActivity.this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}