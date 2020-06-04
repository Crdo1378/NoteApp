package com.example.noteapp.Object;

import java.io.Serializable;
import java.util.Date;

public class NoteOO implements Serializable {
    private String  tagNote, contentNote, titleNote;
    private String timeNote, timeLastChange, timeAlarm;
    private int colorBack, colorText, colorHint, idNote;

    public String getTimeNote() {
        return timeNote;
    }

    public void setTimeNote(String timeNote) {
        this.timeNote = timeNote;
    }

    public String getTimeLastChange() {
        return timeLastChange;
    }

    public void setTimeLastChange(String timeLastChange) {
        this.timeLastChange = timeLastChange;
    }

    public String getTimeAlarm() {
        return timeAlarm;
    }

    public void setTimeAlarm(String timeAlarm) {
        this.timeAlarm = timeAlarm;
    }

    public int getColorBack() {
        return colorBack;
    }

    public void setColorBack(int colorBack) {
        this.colorBack = colorBack;
    }

    public int getColorText() {
        return colorText;
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
    }

    public int getColorHint() {
        return colorHint;
    }

    public void setColorHint(int colorHint) {
        this.colorHint = colorHint;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getTagNote() {
        return tagNote;
    }

    public void setTagNote(String tagNote) {
        this.tagNote = tagNote;
    }

    public String getContentNote() {
        return contentNote;
    }

    public void setContentNote(String contentNote) {
        this.contentNote = contentNote;
    }

    public String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }


}
