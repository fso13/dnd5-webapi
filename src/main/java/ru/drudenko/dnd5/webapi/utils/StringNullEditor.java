package ru.drudenko.dnd5.webapi.utils;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;

public class StringNullEditor extends StringTrimmerEditor {
    public StringNullEditor(boolean emptyAsNull) {
        super(emptyAsNull);
    }

    public StringNullEditor(String charsToDelete, boolean emptyAsNull) {
        super(charsToDelete, emptyAsNull);
    }

    @Override
    public void setAsText(String text) {
        if (text == null || text.equals("null")) {
            setValue(null);
        } else {
            super.setAsText(text);
        }
    }
}
