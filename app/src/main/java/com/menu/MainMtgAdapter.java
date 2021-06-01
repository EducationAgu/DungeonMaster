package com.menu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.typical.mtg.Player;

import java.util.ArrayList;

public class MainMtgAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<Player> listGroup;

    public MainMtgAdapter(Activity context, ArrayList<Player> listGroup) {
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
            convertView = inflater.inflate(R.layout.mtg_main_players, null);
        }

        Player player = (Player) getChild(groupPosition, childPosition);

        TextView health = convertView.findViewById(R.id.TextViewSCHealth);
        health.setText(String.valueOf(player.getHealth()));

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
            convertView = inflater.inflate(R.layout.mtg_player_header, null);
        }

        Player player = listGroup.get(groupPosition);

        TextView name = convertView.findViewById(R.id.mtgNamePlayer);
        name.setText(player.getName());

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
