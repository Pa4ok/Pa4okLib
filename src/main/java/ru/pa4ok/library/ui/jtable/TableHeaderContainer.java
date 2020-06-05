package ru.pa4ok.library.ui.jtable;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TableHeaderContainer
{
    private Object header;
    private boolean editable;

    private KeyAdapter keyFilter = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            //default - allow all
        }
    };

    public TableHeaderContainer(Object header, boolean editable, KeyAdapter keyFilter) {
        this.header = header;
        this.editable = editable;
        if(keyFilter != null) {
            this.keyFilter = keyFilter;
        }
    }

    public TableHeaderContainer(Object header, KeyAdapter keyFilter) {
        this(header, true, keyFilter);
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

    public KeyAdapter getKeyFilter() {
        return keyFilter;
    }

    public void setKeyFilter(KeyAdapter keyFilter) {
        this.keyFilter = keyFilter;
    }
}