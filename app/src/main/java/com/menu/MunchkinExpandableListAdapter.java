package com.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.typical.munchkin.Player;

import java.util.ArrayList;

public class MunchkinExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private ArrayList<Player> listGroup;

    public MunchkinExpandableListAdapter(Activity context, ArrayList<Player> listGroup) {
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
            convertView = inflater.inflate(R.layout.munchkin_player_stats, null);
        }

        Button deletePlayerButton = convertView.findViewById(R.id.btnDeleteMunchkinPlayer);
        deletePlayerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                listGroup.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Player player = (Player) getChild(groupPosition, childPosition);

        TextView level = convertView.findViewById(R.id.munchkinPlayerLevel);
        level.setText(String.valueOf(player.getLevel()));

        TextView equipment = convertView.findViewById(R.id.munchkinPlayerEquipment);
        equipment.setText(String.valueOf(player.getEquipment()));

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
            convertView = inflater.inflate(R.layout.munchkin_player_header, null);
        }

        Player player = listGroup.get(groupPosition);

        TextView name = convertView.findViewById(R.id.munchkinNamePlayer);
        name.setText(player.getName());

        TextView power = convertView.findViewById(R.id.munchkinPlayerPower);
        power.setText(String.valueOf(player.getPowerAmount()));

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
