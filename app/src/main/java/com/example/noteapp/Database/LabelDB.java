package com.example.noteapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.noteapp.Object.Label;
import com.example.noteapp.Object.NoteOO;

public class LabelDB {
    SQLiteDatabase database;
    ConnectDB connectDB;

    public LabelDB(Context context){
        connectDB = new ConnectDB(context);
        try {
            database = connectDB.getWritableDatabase();
        }
        catch (SQLException e){
            database = connectDB.getReadableDatabase();
        }
    }

    public long themLabel(Label label){
        ContentValues values = new ContentValues();
        values.put(ConnectDB.tab_Col_idLabel, label.getIdLabel());
        values.put(ConnectDB.tab_Col_label, label.getLabel());
        return database.insert(ConnectDB.db_Tab_Label, null, values);
    }
    public Cursor danhSachLabel() {
        Cursor cursor = null;
        String[] col = {ConnectDB.tab_Col_idLabel, ConnectDB.tab_Col_label};
        cursor = database.query(ConnectDB.db_Tab_Label, col, null, null, null, null, ConnectDB.tab_Col_idLabel + " DESC ");
        return cursor;
    }
    public long xoaLabel(Label note){
        return database.delete(ConnectDB.db_Tab_Label,
                ConnectDB.tab_Col_idLabel + " = " + note.getIdLabel(),
                null);
    }
    public void closeDB(){
        connectDB.close();
    }
}
