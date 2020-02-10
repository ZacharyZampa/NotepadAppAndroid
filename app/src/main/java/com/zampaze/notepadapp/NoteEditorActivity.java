package com.zampaze.notepadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);

        // find what note is being edited
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId == -1) {
            // note does not exist -- add note
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
        }

        // note was passed through correctly -- lets edit
        editText.setText(MainActivity.notes.get(noteId));

        // update text when user updates it
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                // save to storage
                SharedPreferences sharedPreferences = getApplicationContext().
                        getSharedPreferences("com.zampaze.notepadapp", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
