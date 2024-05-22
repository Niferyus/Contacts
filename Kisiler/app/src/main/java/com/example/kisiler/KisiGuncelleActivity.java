package com.example.kisiler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class KisiGuncelleActivity extends AppCompatActivity {
    EditText editTextName, editTextPhoneNumber;
    Button buttonUpdate;
    Veritabani veritabani;
    int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_guncelle);

        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonUpdate = findViewById(R.id.buttonUpdate);


        veritabani = new Veritabani(this);


        Intent intent = getIntent();
        contactId = intent.getIntExtra("contact_id", -1);


        displayContactDetails();


        buttonUpdate.setOnClickListener(v -> updateContact());
    }

    private void displayContactDetails()
    {
        Kisi kisi = veritabani.getContact(contactId);
        editTextName.setText(kisi.getName());
        editTextPhoneNumber.setText(kisi.getPhone());
    }

    private void updateContact() {

        String newName = editTextName.getText().toString().trim();
        String newPhoneNumber = editTextPhoneNumber.getText().toString().trim();


        int rowsAffected = veritabani.updateContact(contactId, newName, newPhoneNumber);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Kişi güncellendi..", Toast.LENGTH_SHORT).show();
            Intent resultintent = new Intent();
            setResult(RESULT_OK,resultintent);
            finish();
        } else {
            Toast.makeText(this, "Güncelleme işlemi yapılamadı.!", Toast.LENGTH_SHORT).show();
        }
    }
}