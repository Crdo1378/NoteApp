package com.example.noteapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConnectDB extends SQLiteOpenHelper {
    public static String db_Note = "NoteDB";
    public static String db_Table_Note = "Note";
    public static String db_Tab_Label = "Tab";

    public static String tab_Col_label = "label";
    public static String tab_Col_idLabel = "id";
    public static String note_Col_idNote = "idnote";
    public static String note_Col_timeNote = "timeNote";
    public static String note_Col_tag = "tag";
    public static String note_Col_tittle = "title";
    public static String note_Col_content = "content";
    public static String note_Col_colorBack = "colorback";
    public static String note_Col_colorText = "colortext";
    public static String note_Col_colorhint = "colorhint";
    public static String note_Col_timeLastChange = "timeLastChange";
    public static String note_Col_timeAlarm = "timeAlarm";
    public static String note_Col_resourceBack = "resourceback";

    public static String create_DB =
            " CREATE TABLE " + db_Table_Note + " ( "
                    + note_Col_idNote + " INTEGER PRIMARY KEY, "
                    + note_Col_colorBack + " INTEGER, "
                    + note_Col_resourceBack + " INTEGER, "
                    + note_Col_colorText + " INTEGER, "
                    + note_Col_colorhint + " INTEGER, "
                    + note_Col_content + " TEXT NOT NULL, "
                    + note_Col_tag + " TEXT , "
                    + note_Col_tittle + " TEXT , "
                    + note_Col_timeNote + " TEXT NOT NULL, "
                    + note_Col_timeLastChange + " TEXT NOT NULL, "
                    + note_Col_timeAlarm + " TEXT ) " ;


    public static String creLabel =
            " CREATE TABLE " + db_Tab_Label + " ( "
                    + tab_Col_idLabel + " INTEGER PRIMARY KEY, "
                    + tab_Col_label + " TEXT ) " ;
    public ConnectDB(@Nullable Context context) {
        super(context, db_Note, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_DB);
        db.execSQL(creLabel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
