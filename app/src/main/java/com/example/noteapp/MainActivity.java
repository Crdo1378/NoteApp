package com.example.noteapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.noteapp.Adapter.Adapter_1note;
import com.example.noteapp.Adapter.ClickListener;
import com.example.noteapp.Database.LabelDB;
import com.example.noteapp.Database.NoteDB;
import com.example.noteapp.Object.Label;
import com.example.noteapp.Object.NoteOO;
import com.example.noteapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SearchView searchView;

    private  Adapter_1note adapter;
    private ArrayList<NoteOO> listNote;
    public static NoteOO noteAtTime;
    private NoteDB noteDB;

    private LabelDB labelDB;
    private ArrayList<Label> strLabel;
    private ArrayList<String> chuoiLabel;
    private String labelSelect;
    private Label labelAtTime;
    private ArrayAdapter arrayAdapter;

    //backtoExit
    private long backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        strLabel = new ArrayList<>();
        listNote = new ArrayList<>();
        chuoiLabel = new ArrayList<>();
        adapter = new Adapter_1note(listNote, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                noteAtTime = listNote.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("noteAtTime", noteAtTime);
                intent.putExtra("checkNew", false);
                intent.putExtra("listLast", listNote.get(0).getIdNote());
                intent.putExtra("listLabel", chuoiLabel);
                startActivity(intent);
                MainActivity.this.finish();
            }

            @Override
            public void onLongClicked(int position) {

            }
        });
        listNote = listFromDB();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvAllNote.setLayoutManager(layoutManager);
        binding.rvAllNote.setAdapter(adapter);

        arrayAdapter = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                chuoiLabel
        );
        strLabel = listLBFromDB();

        binding.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("checkNew", true);
                intent.putExtra("listLabel", chuoiLabel);
                if(!listNote.isEmpty()){
                    intent.putExtra("listLast", listNote.get(0).getIdNote());
                }
                else {
                    intent.putExtra("listLast", 0);
                }
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) menu.findItem(R.id.menuSearchMain).getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSearchMain:
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String s) {
                        String text = s;
                        adapter.filter(text);
                        return false;
                    }
                });
                Log.i("Crdo", "click Post");
                return true;
            case R.id.menuTabMain:
                openTab();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(backPress + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            return;
        }
        else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPress = System.currentTimeMillis();
    }

    private void openTab() {
        //Toast.makeText(this, "TAB", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_label_tab);
        dialog.show();

        final ListView lvLabel = dialog.findViewById(R.id.lvTabLabel);
        final EditText txAddLabel = dialog.findViewById(R.id.txAddLb);
        final ImageButton btAddLabel = dialog.findViewById(R.id.btAddLabel);


        if(!strLabel.isEmpty()){
            lvLabel.setAdapter(arrayAdapter);
            lvLabel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    labelSelect = strLabel.get(position).getLabel();
                    //Toast.makeText(MainActivity.this, labelSelect, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
        }

        btAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelSelect = txAddLabel.getText().toString();
                if(strLabel.size()==0){
                    labelAtTime = new Label(labelSelect,0);
                }
                else {
                    labelAtTime = new Label(labelSelect,strLabel.get(0).getIdLabel()+1);
                }

                if(labelAtTime.getLabel().equals("")){
                    Toast.makeText(MainActivity.this, "Label chưa được tạo", Toast.LENGTH_SHORT).show();
                }
                else {
                    labelDB = new LabelDB(MainActivity.this);
                    labelDB.themLabel(labelAtTime);
                    noteDB.closeDB();
                    Toast.makeText(MainActivity.this, "Create Label Success", Toast.LENGTH_SHORT).show();
                }
                strLabel = listLBFromDB();
                dialog.cancel();
            }
        });

        lvLabel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lvLabel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertDel = new AlertDialog.Builder(MainActivity.this);
                alertDel.setTitle(getResources().getString(R.string.notification));
                alertDel.setMessage(getResources().getString(R.string.noti_del_label));
                alertDel.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDel.setPositiveButton(getResources().getString(R.string.del), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LabelDB labelDB = new LabelDB(MainActivity.this);
                        labelDB.xoaLabel(strLabel.get(position));
                        labelDB.closeDB();
                        strLabel = listLBFromDB();
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.delSuc), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDel.create();
                alertDialog.show();
                return true;
            }
        });
    }

    private ArrayList<NoteOO> listFromDB(){
        noteDB = new NoteDB(this);
        Cursor cursor = noteDB.danhSachNote();
        if(cursor!=null){
            listNote.clear();
            while (cursor.moveToNext()){
                noteAtTime = new NoteOO();
                noteAtTime.setIdNote(cursor.getInt(0));
                noteAtTime.setColorBack(cursor.getInt(1));
                noteAtTime.setResoureBack(cursor.getInt(2));
                noteAtTime.setColorText(cursor.getInt(3));
                noteAtTime.setColorHint(cursor.getInt(4));
                noteAtTime.setContentNote(cursor.getString(5));
                noteAtTime.setTagNote(cursor.getString(6));
                noteAtTime.setTitleNote(cursor.getString(7));
                noteAtTime.setTimeNote(cursor.getString(8));
                noteAtTime.setTimeLastChange(cursor.getString(9));
                noteAtTime.setTimeAlarm(cursor.getString(10));
                listNote.add(noteAtTime);
            }
        }
        adapter.notifyDataSetChanged();
        return listNote;
    }

    private ArrayList<Label> listLBFromDB(){
        labelDB = new LabelDB(this);
        Cursor cursor = labelDB.danhSachLabel();
        if(cursor!=null){
            strLabel.clear();
            chuoiLabel.clear();
            while (cursor.moveToNext()){
                labelAtTime = new Label();
                labelAtTime.setIdLabel(cursor.getInt(0));
                labelAtTime.setLabel(cursor.getString(1));
                strLabel.add(labelAtTime);
                chuoiLabel.add(labelAtTime.getLabel());
            }
        }
        arrayAdapter.notifyDataSetChanged();;
        return strLabel;
    }

}
