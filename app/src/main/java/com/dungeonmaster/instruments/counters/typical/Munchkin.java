package com.dungeonmaster.instruments.counters.typical;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.dungeonmaster.R;
import com.menu.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Munchkin extends Activity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expListAdapter;
    ArrayList<View> listDataHeader;
    ArrayList<ArrayList<View>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manchcken);

        expandableListView = findViewById(R.id.listOfMunchkinPlayers);
        createStartGroup();
        expListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expListAdapter);
    }

    private void createStartGroup() {
        listDataChild = new ArrayList<>();
        listDataHeader = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            listDataHeader.add(findViewById(R.id.munchkinPlayersHeader));
            ArrayList<View> child = new ArrayList<>();
            child.add(findViewById(R.id.munchkinPlayersHeader));
            listDataChild.add(child);
        }
    }

    public void onClickAddPlayer(View view) {
        View userLayout = getLayoutInflater().inflate(R.layout.munchkin_player_header, null, false);
        expandableListView.addView(userLayout);
    }

    /*
    *
    * TODO вроде как лишнее, подумать и убрать
    *
    *
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onClickOpenPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_bar);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали PopupMenu 1",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu2:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали PopupMenu 2",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu3:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали PopupMenu 3",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "onDismiss",
                        Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }*/
}