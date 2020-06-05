package ru.pa4ok.library.ui.jtable;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.List;

public abstract class EditableTableModel<T> extends DataTableModel<T>
{
    protected final List<TableHeaderContainer> headers = new ArrayList<>();

    public EditableTableModel(JTable tableInstanceIn)
    {
        super(tableInstanceIn);

        getTableHeaders(this.headers);
        for(TableHeaderContainer header : this.headers) {
            addColumn(header.getHeader());
        }

        addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                onTableChangeEvent(row, getRowObject(row));
            }
        });
    }

    public abstract void getTableHeaders(List<TableHeaderContainer> headers);

    public abstract void onTableChangeEvent(int row, T object);

    @Override
    public boolean isCellEditable(int row, int column) {
        if(column >= headers.size()) {
            return  false;
        }

        return headers.get(column).isEditable();
    }

    public List<TableHeaderContainer> getHeaders() {
        return headers;
    }
}