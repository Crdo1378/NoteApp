package com.example.noteapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.noteapp.Object.NoteOO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteDB {
    SQLiteDatabase database;
    ConnectDB connectDB;

    public NoteDB(Context context){
        connectDB = new ConnectDB(context);
        try {
            database = connectDB.getWritableDatabase();
        }
        catch (SQLException e){
            database = connectDB.getReadableDatabase();
        }
    }

    public long themNote(NoteOO note){
        ContentValues values = new ContentValues();

        values.put(ConnectDB.note_Col_colorhint, note.getColorHint());
        values.put(ConnectDB.note_Col_colorText, note.getColorText());
        values.put(ConnectDB.note_Col_colorBack, note.getColorBack());
        values.put(ConnectDB.note_Col_resourceBack, note.getResoureBack());
        values.put(ConnectDB.note_Col_content, note.getContentNote());
        values.put(ConnectDB.note_Col_idNote, note.getIdNote());
        values.put(ConnectDB.note_Col_tag, note.getTagNote());
        values.put(ConnectDB.note_Col_tittle, note.getTitleNote());

        values.put(ConnectDB.note_Col_timeAlarm, note.getTimeAlarm());
        values.put(ConnectDB.note_Col_timeLastChange, note.getTimeLastChange());
        values.put(ConnectDB.note_Col_timeNote, note.getTimeNote());

        return database.insert(ConnectDB.db_Table_Note, null, values);
    }

    public Cursor danhSachNote(){
        Cursor cursor = null;
        String[] col = {ConnectDB.note_Col_idNote,
                ConnectDB.note_Col_colorBack,
                ConnectDB.note_Col_resourceBack,
                ConnectDB.note_Col_colorText,
                ConnectDB.note_Col_colorhint,
                ConnectDB.note_Col_content,
                ConnectDB.note_Col_tag,
                ConnectDB.note_Col_tittle,
                ConnectDB.note_Col_timeNote,
                ConnectDB.note_Col_timeLastChange,
                ConnectDB.note_Col_timeAlarm};
        //trỏ đến kết quả của câu query
        cursor = database.query(ConnectDB.db_Table_Note, col, null, null, null, null,
                ConnectDB.note_Col_idNote + " DESC ");
        return cursor;
    }

    public long suaPhieuCap(NoteOO note){
        ContentValues values = new ContentValues();

        values.put(ConnectDB.note_Col_colorhint, note.getColorHint());
        values.put(ConnectDB.note_Col_colorText, note.getColorText());
        values.put(ConnectDB.note_Col_colorBack, note.getColorBack());
        values.put(ConnectDB.note_Col_resourceBack, note.getResoureBack());
        values.put(ConnectDB.note_Col_content, note.getContentNote());
        values.put(ConnectDB.note_Col_idNote, note.getIdNote());
        values.put(ConnectDB.note_Col_tag, note.getTagNote());
        values.put(ConnectDB.note_Col_tittle, note.getTitleNote());

        values.put(ConnectDB.note_Col_timeAlarm, note.getTimeAlarm());
        values.put(ConnectDB.note_Col_timeLastChange, note.getTimeLastChange());

        return database.update(ConnectDB.db_Table_Note, values,
                ConnectDB.note_Col_idNote + " = " + note.getIdNote(),
                null);
    }

    public long xoaPhieuCap(NoteOO note){
        return database.delete(ConnectDB.db_Table_Note,
                ConnectDB.note_Col_idNote + " = " + note.getIdNote(),
                null);
    }

    public void closeDB(){
        connectDB.close();
    }
}
