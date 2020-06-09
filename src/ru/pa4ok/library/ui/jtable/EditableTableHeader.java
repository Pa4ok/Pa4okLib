package ru.pa4ok.library.ui.jtable;

import ru.pa4ok.library.util.DataFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditableTableHeader
{
    private Object header;
    private boolean editable;
    private DataFilter<String> filter;

    public EditableTableHeader(Object header, boolean editable, DataFilter<String> filter) {
        this.header = header;
        this.editable = editable;
        this.filter = filter;
    }

    public EditableTableHeader(Object header, DataFilter<String> filter) {
        this(header, true, filter);
    }

    public EditableTableHeader(Object header, boolean editable) {
        this(header, editable, DataFilter.allowFilter);
    }

    public EditableTableHeader(Object header) {
        this(header, DataFilter.allowFilter);
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public DataFilter<String> getFilter() {
        return filter;
    }

    public void setFilter(DataFilter<String> filter) {
        this.filter = filter;
    }
}