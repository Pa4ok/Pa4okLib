package ru.pa4ok.library.ui.jtable;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.List;

public abstract class EditableTableModel<T> extends DataTableModel<T>
{
    protected final List<TableHeaderContainer> headers = new ArrayList<>();
    protected boolean enableChangeEvent = true;

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
                if(!enableChangeEvent) {
                    return;
                }

                int row = e.getFirstRow();
                int column = e.getColumn();
                String newCellData = (String)tableInstance.getValueAt(row, column);

                if(getRowDataFromObject(tableContent.get(row))[column].equals(newCellData)) {
                    return;
                }

                if(!headers.get(column).getFilter().filter(newCellData)) {
                    tableInstance.setValueAt(getRowDataFromObject(tableContent.get(row))[column], row, column);
                    return;
                }

                T newObject = getRowObject(row);
                tableContent.set(row, newObject);
                onTableChangeEvent(row, newObject);
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

    public void hideAddRow(T object)
    {
        this.enableChangeEvent = false;
        addRow(object);
        this.enableChangeEvent = true;
    }

    public void hideAddRows(List<T> objects)
    {
        for(T object : objects) {
            hideAddRow(object);
        }
    }

    public void hideAddRow(Object[] rowData) {
        this.enableChangeEvent = false;
        super.addRow(rowData);
        this.tableContent.add(getObjectFromData(rowData));
        this.enableChangeEvent = true;
    }

    public void hideRemoveRow(int row) {
        this.enableChangeEvent = false;
        super.removeRow(row);
        this.tableContent.remove(row);
        this.enableChangeEvent = true;
    }

    public void hideClearTableContent()
    {
        int count = getRowCount();
        for (int i = count - 1; i >= 0; i--) {
            hideRemoveRow(i);
        }
        this.tableContent.clear();
    }


    public List<TableHeaderContainer> getHeaders() {
        return headers;
    }

    public boolean isEnableChangeEvent() {
        return enableChangeEvent;
    }

    public void setEnableChangeEvent(boolean enableChangeEvent) {
        this.enableChangeEvent = enableChangeEvent;
    }
}