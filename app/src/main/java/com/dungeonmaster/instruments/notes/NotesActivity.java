package com.dungeonmaster.instruments.notes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.dungeonmaster.R;
import com.dungeonmaster.menu.MainMenu;
import com.google.gson.Gson;
import com.menu.MenuBar;
import com.menu.NoteExpandableListAdapter;
import com.serverconnection.Server;
import com.serverconnection.URLs;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class NotesActivity extends MenuBar {

    ExpandingList expandingList;
    NoteExpandableListAdapter expListAdapter;

    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notes);

        expandingList = findViewById(R.id.listOfNotes);

        Server.passRequest(HttpMethod.GET, URLs.NOTE, "");
        ResponseEntity<String> response = Server.getQueryResult(URLs.NOTE);
        String notesRaw = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            notesRaw = response.getBody();
        } else {
            Toast.makeText(this, "Ошибка соединения с сервером", Toast.LENGTH_SHORT).show();
            setNextScreen(MainMenu.class, TO_RIGHT);
        }
        notes = new ArrayList<>();
        if (notesRaw != null && notesRaw.length() > 0) {
            Note[] notesArr = (new Gson()).fromJson(notesRaw, Note[].class);
            for (int i = 0; i < notesArr.length; i++) {
                addNote(notesArr[i]);
            }
        }

    }

    public void addNote(Note noteIn){
        Note note;
        if (noteIn != null){
            note = noteIn;
        } else {
            note = new Note();
        }
        notes.add(note);

        ExpandingItem item = expandingList.createNewItem(R.layout.notes_list_item);
        item.createSubItems(1);

        TextView textView = item.findViewById(R.id.noteOuterTittle);
        textView.setOnClickListener(v -> item.toggleExpanded());
        textView.setText(note.getName());

        EditText editableTittle = item.findViewById(R.id.NoteTitle);
        editableTittle.setText(note.getName());

        EditText editableDesc = item.findViewById(R.id.NoteDisc);
        editableDesc.setText(note.getNotes());

        ImageButton edit = item.findViewById(R.id.imageButtonEditNote);
        edit.setOnClickListener(v -> {
            editableTittle.setEnabled(!editableTittle.isEnabled());
            editableDesc.setEnabled(!editableDesc.isEnabled());
            if (!item.isExpanded()) {
                item.toggleExpanded();
            }
        });
        ImageButton delete = item.findViewById(R.id.imageButtonRemoveNote);
        delete.setOnClickListener(v -> {
            notes.remove(note);
            expandingList.removeItem(item);
            if (note.getId() != 0) {
                Server.passRequest(HttpMethod.POST, URLs.DROP_NOTE+"/" + note.getId(), "");
            }
        });

        Button saveNote = item.findViewById(R.id.btnSaveNote);
        saveNote.setOnClickListener(v -> {
            note.setName(editableTittle.getText().toString());
            textView.setText(note.getName());
            note.setNotes(editableDesc.getText().toString());

            Server.passRequest(HttpMethod.POST, URLs.NOTE, note);
            editableTittle.setEnabled(false);
            editableDesc.setEnabled(false);

            ResponseEntity<String> response = Server.getQueryResult(URLs.NOTE);
            if (response.getStatusCode() == HttpStatus.OK) {
                note.setId(Long.parseLong(response.getBody()));
            }
        });

        if (note.getId() != null) {
            editableTittle.setEnabled(false);
            editableDesc.setEnabled(false);
        }

        expListAdapter = new NoteExpandableListAdapter(this, notes);
    }

    public void onClickAddNote(View view){
        addNote(null);
    }

}
