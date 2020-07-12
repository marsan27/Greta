package com.marsanpat.greta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marsanpat.greta.R;
import com.marsanpat.greta.Utils.Notes.NoteManager;
import com.marsanpat.greta.ui.notes.NotesFragment;

public class EditNoteActivity extends AppCompatActivity {
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        final String startingText = getIntent().getStringExtra("Initial Text");
        final long detectedId = getIntent().getLongExtra("ID", 0);
        final String encryptionPassword = getIntent().getStringExtra("password");

        text = (EditText)findViewById(R.id.inputNote);
        text.setText(startingText);

        Button saveBut = findViewById(R.id.saveBut);
        saveBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = text.getText().toString();
                if(encryptionPassword!=null){ //Means we need to encrypt the element later
                    if(NoteManager.saveNoteAndEncrypt(input, detectedId, encryptionPassword, true)==-1){
                        //error
                        Toast.makeText(getApplicationContext(), "Please write something before saving", Toast.LENGTH_LONG)
                                .show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_LONG)
                                .show();
                        finish();
                    }
                }else{
                    if(NoteManager.saveNote(input, detectedId, false)==-1){
                        //error
                        Toast.makeText(getApplicationContext(), "Please write something before saving", Toast.LENGTH_LONG)
                                .show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_LONG)
                                .show();
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
            }
        });

        Button exitBut = findViewById(R.id.exitBut);
        exitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private String calculatePreview(String txt){
        if(txt.length()> NotesFragment.MAXIMUM_PREVIEW_LENGTH){
            return txt.substring(0,16)+"...";
        }
        return txt;
    }
}