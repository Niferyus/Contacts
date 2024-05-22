package com.example.kisiler;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Veritabani veritabani;
    Cursor cursor;
    int selectedContactId;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.contact_list);
        veritabani = new Veritabani(this);


        cursor = veritabani.getAllContacts();


        String[] from = {"name", "phone"};
        int[] to = {R.id.text1, R.id.text2};
        adapter = new SimpleCursorAdapter(this, R.layout.kisigorunum, cursor, from, to, 0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < listView.getChildCount(); i++) {
                View listItem = listView.getChildAt(i);
                Button buttonUpdate = listItem.findViewById(R.id.buttonUpdate);
                Button buttonDelete = listItem.findViewById(R.id.buttonDelete);
                buttonUpdate.setVisibility(View.GONE);
                buttonDelete.setVisibility(View.GONE);
            }

            cursor.moveToPosition(position);
            int columnIndex = cursor.getColumnIndex("_id");
            if (columnIndex != -1) {
                selectedContactId = cursor.getInt(columnIndex);
                Button buttonUpdate = view.findViewById(R.id.buttonUpdate);
                Button buttonDelete = view.findViewById(R.id.buttonDelete);
                buttonUpdate.setVisibility(View.VISIBLE);
                buttonDelete.setVisibility(View.VISIBLE);

                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, KisiGuncelleActivity.class);
                        intent.putExtra("contact_id", selectedContactId);
                        startActivityForResult(intent,1);
                    }
                });

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int deletedRows = veritabani.deleteContact(selectedContactId);
                        if (deletedRows > 0) {
                            Toast.makeText(MainActivity.this, "Kişi silindi..", Toast.LENGTH_SHORT).show();
                            cursor = veritabani.getAllContacts();
                            adapter.changeCursor(cursor);
                        } else {
                            Toast.makeText(MainActivity.this, "Silme işlemi başarısız.!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "Error:Kişi Bulunamadı.!", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonAddContact = findViewById(R.id.buttonAddContact);
        buttonAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KisiekleActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                cursor = veritabani.getAllContacts();
                adapter.changeCursor(cursor);
            }
        }
    }
}