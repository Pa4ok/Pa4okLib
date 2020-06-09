package ru.pa4ok.library.ui.jtable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class DataTableModel<T> extends DefaultTableModel
{
    protected final JTable tableInstance;

    protected final List<T> tableContent = new ArrayList<>();

    public DataTableModel(JTable tableInstance)
    {
        this.tableInstance = tableInstance;
        this.tableInstance.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    protected abstract T getObjectFromData(Object[] data);

    protected abstract String[] getRowDataFromObject(T obj);

    protected Object[] getRowData(int row)
    {
        Object[] data = new Object[getColumnCount()];
        for(int i = 0; i< getColumnCount(); i++) {
            data[i] = getValueAt(row, i);
        }
        return  data;
    }

    protected void setRowData(int row, Object[] columns)
    {
        for(int i=0; i<getColumnCount(); i++) {
            setValueAt(columns[i], row, i);
        }
    }

    public T getRowObject(int row)
    {
        return getObjectFromData(getRowData(row));
    }

    public void addRow(T object)
    {
        addRow(getRowDataFromObject(object));
        this.tableContent.add(object);
    }

    public void addRows(List<T> objects)
    {
        for(T object : objects) {
            addRow(object);
        }
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        this.tableContent.remove(row);
    }

    public void setRow(int row, T object)
    {
        setRowData(row, getRowDataFromObject(object));
        this.tableContent.set(row, object);
    }

    public void clearTableContent()
    {
        int count = getRowCount();
        for (int i = count - 1; i >= 0; i--) {
            removeRow(i);
        }
        this.tableContent.clear();
    }

    public JTable getTableInstance() {
        return tableInstance;
    }

    public List<T> getTableContent() {
        return tableContent;
    }
}
