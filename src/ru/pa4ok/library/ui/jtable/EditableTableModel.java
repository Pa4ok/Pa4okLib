package ru.pa4ok.library.ui.jtable;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.List;

public abstract class EditableTableModel<T> extends DataTableModel<T>
{
    protected List<EditableTableHeader> headers;
    protected boolean enableChangeEvent = true;

    public EditableTableModel(JTable tableInstanceIn)
    {
        super(tableInstanceIn);

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

    @Override
    protected Object[] getTableHeaders() {
        return null;
    }

    @Override
    protected void initTableHeaders() {
        this.headers = new ArrayList<>();
        getTableHeaders(this.headers);
        for(EditableTableHeader header : this.headers) {
            addColumn(header.getHeader());
        }
    }

    protected abstract void getTableHeaders(List<EditableTableHeader> headers);

    protected abstract void onTableChangeEvent(int row, T object);

    @Override
    public boolean isCellEditable(int row, int column) {
        if(column >= headers.size()) {
            return  false;
        }

        return headers.get(column).isEditable();
    }

    public void addRow(T object, boolean doChangeEvent)
    {
        enableChangeEvent = doChangeEvent;
        addRow(getRowDataFromObject(object));
        this.tableContent.add(object);
        enableChangeEvent = true;
    }

    @Override
    public void addRow(T object) {
        addRow(object, true);
    }

    public void addRows(List<T> objects, boolean doChangeEvent)
    {
        for(T object : objects) {
            addRow(object, doChangeEvent);
        }
    }

    @Override
    public void addRows(List<T> objects)
    {
        for(T object : objects) {
            addRow(object, true);
        }
    }

    public void removeRow(int row, boolean doChangeEvent) {
        enableChangeEvent = doChangeEvent;
        super.removeRow(row);
        this.tableContent.remove(row);
        enableChangeEvent = true;
    }

    @Override
    public void removeRow(int row) {
        removeRow(row, true);
    }

    public void setRow(int row, T object, boolean doChangeEvent)
    {
        enableChangeEvent = doChangeEvent;
        setRowData(row, getRowDataFromObject(object));
        this.tableContent.set(row, object);
        enableChangeEvent = true;
    }

    @Override
    public void setRow(int row, T object)
    {
        setRow(row, object, true);
    }

    public void clearTableContent(boolean doChangeEvent)
    {
        int count = getRowCount();
        for (int i = count - 1; i >= 0; i--) {
            removeRow(i, doChangeEvent);
        }
        this.tableContent.clear();
    }

    @Override
    public void clearTableContent()
    {
        clearTableContent(true);
    }

    public List<EditableTableHeader> getHeaders() {
        return headers;
    }

    public boolean isEnableChangeEvent() {
        return enableChangeEvent;
    }

    public void setEnableChangeEvent(boolean enableChangeEvent) {
        this.enableChangeEvent = enableChangeEvent;
    }
}