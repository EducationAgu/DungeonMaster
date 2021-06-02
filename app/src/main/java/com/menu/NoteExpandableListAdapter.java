package com.menu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.notes.Note;

import java.util.ArrayList;

public class NoteExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<Note> listGroup;

    public NoteExpandableListAdapter(Activity context, ArrayList<Note> listGroup) {
        this.context = context;
        this.listGroup = listGroup;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.notes_stats, null);
        }

        Note note = (Note) getChild(groupPosition, childPosition);

        TextView tittle = convertView.findViewById(R.id.NoteTitle);
        tittle.setText(String.valueOf(note.getName()));

        TextView desc = convertView.findViewById(R.id.NoteDisc);
        desc.setText(String.valueOf(note.getNotes()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notes_header, null);
        }

        Note note = listGroup.get(groupPosition);

        TextView name = convertView.findViewById(R.id.noteOuterTittle);
        name.setText(note.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
