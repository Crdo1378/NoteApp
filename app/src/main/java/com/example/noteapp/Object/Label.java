package com.example.noteapp.Object;

import java.io.Serializable;

public class Label implements Serializable {
    private String label;
    private int idLabel;

    public int getIdLabel() {
        return idLabel;
    }

    public void setIdLabel(int idLabel) {
        this.idLabel = idLabel;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Label(String label, int idLabel) {
        this.label = label;
        this.idLabel = idLabel;
    }

    public Label() {
    }
}
