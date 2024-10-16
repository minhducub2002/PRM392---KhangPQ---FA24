package com.fpt.khangpq.se1730sqlite;

import android.annotation.SuppressLint;
import android.database.Cursor;
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

public class MainActivity extends AppCompatActivity {

    private Button btnLoad;
    private Button btnSave;
    private EditText edtWord;
    private EditText edtDefinition;
    private EditText edtData;
    private DBContext db;

    private void bindingView() {
        btnLoad = findViewById(R.id.btnLoad);
        btnSave = findViewById(R.id.btnSave);
        edtWord = findViewById(R.id.edtWord);
        edtDefinition = findViewById(R.id.edtDefinition);
        edtData = findViewById(R.id.edtData);
        db = new DBContext(this);
    }

    private void bindingAction() {
        btnLoad.setOnClickListener(this::onBtnLoadClick);
        btnSave.setOnClickListener(this::onBtnSaveClick);
    }

    private void onBtnSaveClick(View view) {
        String word = edtWord.getText().toString();
        String definition = edtDefinition.getText().toString();
        long id = db.insert2(word, definition);
        Toast.makeText(this, id < 0 ? "Lỗi" : "Thành công id=" + id, Toast.LENGTH_SHORT).show();
    }

    private void onBtnLoadClick(View view) {
        Cursor cursor = db.getAll();
        edtData.setText("");
        if (cursor.moveToFirst()) {
            do {
                int colIdIndex = cursor.getColumnIndex(DBContext.TB_DICTIONARY_ID);
                int id = cursor.getInt(colIdIndex);
                @SuppressLint("Range") String word = cursor.getString(cursor.getColumnIndex(DBContext.TB_DICTIONARY_WORD));
                int colDefIndex = cursor.getColumnIndex(DBContext.TB_DICTIONARY_DEFINITION);
                String definition = cursor.getString(colDefIndex);
                edtData.append(id + " - " + word + " - " + definition + "\n");
            } while (cursor.moveToNext());
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}