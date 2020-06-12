package ru.pa4ok.library.ui.jtable;

import ru.pa4ok.library.ui.DialogUtil;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

        tableInstance.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int selectedRow = tableInstance.getSelectedRow();
                    if(DialogUtil.showConfirm("Вы точно хотите удалить этот объект?"))
                    {
                        T object = getRowObject(selectedRow);
                        tableInstance.removeEditor();
                        tableInstance.clearSelection();
                        removeRow(selectedRow, false);
                        onTableRemoveRowEvent(object);
                    }
                }
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

    protected abstract void onTableRemoveRowEvent(T object);

    protected abstract T onTableCreateEntryEvent(String[] values);

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
        super.addRow(object);
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
        enableChangeEvent = true;
    }

    @Override
    public void removeRow(int row) {
        removeRow(row, true);
    }

    public void setRow(int row, T object, boolean doChangeEvent)
    {
        enableChangeEvent = doChangeEvent;
        super.setRow(row, object);
        enableChangeEvent = true;
    }

    @Override
    public void setRow(int row, T object)
    {
        setRow(row, object, true);
    }

    public void clearTableContent(boolean doChangeEvent)
    {
        enableChangeEvent = doChangeEvent;
        super.clearTableContent();
        enableChangeEvent = true;
    }

    @Override
    public void clearTableContent()
    {
        clearTableContent(true);
    }

    @Override
    public void refillTableFromList()
    {
        for (int i = getRowCount() - 1; i >= 0; i--) {
            dataVector.removeElementAt(i);
            enableChangeEvent = false;
            fireTableRowsDeleted(i, i);
            enableChangeEvent = true;
        }

        for(T o : this.tableContent) {
            enableChangeEvent = false;
            addRow(getRowDataFromObject(o));
            enableChangeEvent = true;
        }
    }

    public void showCreateEntryForm()
    {
        T object = onTableCreateEntryEvent(CreateTableEntryForm.createTableEntryData(this.headers));
        if(object != null) {
            addRow(object, false);
        }
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