package ru.pa4ok.library.ui.jtable;

import ru.pa4ok.library.util.DataFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TableHeaderContainer
{
    private Object header;
    private boolean editable;
    private DataFilter<String> filter;

    public TableHeaderContainer(Object header, boolean editable, DataFilter<String> filter) {
        this.header = header;
        this.editable = editable;
        if(filter == null) {
            this.filter = DataFilter.allowFilter;
        } else {
            this.filter = filter;
        }
    }

    public TableHeaderContainer(Object header, DataFilter<String> filter) {
        this(header, true, filter);
    }

    public TableHeaderContainer(Object header, boolean editable) {
        this(header, editable, null);
    }

    public TableHeaderContainer(Object header) {
        this(header, null);
    }

    public static KeyAdapter getIntegerOnlyFilter()
    {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char ch = e.getKeyChar();

                if(ch < '0' || ch > '9') {
                    e.consume();
                }
            }
        };
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