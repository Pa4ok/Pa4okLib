package ru.pa4ok.library.swing.jtable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ObjectTableModel<T> extends DefaultTableModel
{
    protected final JTable tableInstance;

    protected final List<T> tableContent = new ArrayList<>();

    public ObjectTableModel(JTable tableInstance)
    {
        this.tableInstance = tableInstance;
        this.tableInstance.getTableHeader().setReorderingAllowed(false);
        initTableHeaders();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    protected abstract Object[] getTableHeaders();

    protected abstract T getObjectFromData(String[] rowData);

    protected abstract String[] getRowDataFromObject(T entity);

    protected void initTableHeaders()
    {
        for(Object o : this.getTableHeaders()) {
            addColumn(o);
        }
    }

    protected String[] getRowData(int row)
    {
        String[] data = new String[getColumnCount()];
        for(int i = 0; i< getColumnCount(); i++) {
            data[i] = String.valueOf(getValueAt(row, i));
        }
        return data;
    }

    protected void setRowData(int row, Object[] rowData)
    {
        for(int i=0; i<getColumnCount(); i++) {
            setValueAt(rowData[i], row, i);
        }
    }

    public T getRowObject(int row)
    {
        return getObjectFromData(getRowData(row));
    }

    public void addRow(T entity)
    {
        addRow(getRowDataFromObject(entity));
        this.tableContent.add(entity);
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

    public void setRow(int row, T entity)
    {
        setRowData(row, getRowDataFromObject(entity));
        this.tableContent.set(row, entity);
    }

    public void clearTableContent()
    {
        for (int i = getRowCount() - 1; i >= 0; i--) {
            removeRow(i);
        }
        this.tableContent.clear();
    }

    public void refillTableFromList()
    {
        for (int i = getRowCount() - 1; i >= 0; i--) {
            super.removeRow(i);
        }

        for(T o : this.tableContent) {
            addRow(getRowDataFromObject(o));
        }
    }

    public void sortTable(Comparator<T> sorter) {
        Collections.sort(tableContent, sorter);
        refillTableFromList();
    }

    public JTable getTableInstance() {
        return tableInstance;
    }

    public List<T> getTableContent() {
        return tableContent;
    }
}
