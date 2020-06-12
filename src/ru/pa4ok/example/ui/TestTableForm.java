package ru.pa4ok.example.ui;

import ru.pa4ok.example.Main;
import ru.pa4ok.example.data.entity.SlotEntity;
import ru.pa4ok.example.data.manager.SlotEntityManager;
import ru.pa4ok.library.ui.DialogUtil;
import ru.pa4ok.library.ui.form.BaseForm;
import ru.pa4ok.library.ui.jtable.EditableTableModel;
import ru.pa4ok.library.ui.jtable.EditableTableHeader;
import ru.pa4ok.library.util.DataFilter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestTableForm extends BaseForm
{
    private JPanel mainPanel;
    private JTable table;
    private JButton sortButton1;
    private JButton sortButton2;
    private JButton addButton;

    private final TestTableForm instance;
    private EditableTableModel<SlotEntity> tableModel;
    private SlotEntityManager slotManager = new SlotEntityManager(Main.getInstance().getDatabase());

    public TestTableForm()
    {
        instance = this;
        setContentPane(mainPanel);

        tableModel = new EditableTableModel<SlotEntity>(table) {
            @Override
            public void getTableHeaders(List<EditableTableHeader> headers) {
                headers.add(new EditableTableHeader("id", true, false));
                headers.add(new EditableTableHeader("title"));
                headers.add(new EditableTableHeader("price", DataFilter.positiveIntFilter));
            }

            @Override
            public SlotEntity getObjectFromData(Object[] data) {
                return new SlotEntity(Integer.parseInt((String)data[0]), (String)data[1], Integer.parseInt((String)data[2]));
            }

            @Override
            public String[] getRowDataFromObject(SlotEntity obj) {
                return new String[] { String.valueOf(obj.getId()), obj.getTitle(), String.valueOf(obj.getPrice()) };
            }

            @Override
            public void onTableChangeEvent(int row, SlotEntity object) {
                System.out.println("Change event " + row + " " + object);
                try {
                    slotManager.updateSlot(object);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                    DialogUtil.showError(instance, "Ошибка подключения к бд при редактировании записи");
                }
            }

            @Override
            protected void onTableRemoveRowEvent(SlotEntity object) {
                System.out.println("Delete event " + object);
                try {
                    slotManager.deleteSlotById(object.getId());
                } catch (SQLException exception) {
                    exception.printStackTrace();
                    DialogUtil.showError(instance, "Ошибка подключения к бд при удалении записи");
                }
            }

            @Override
            protected SlotEntity onTableCreateEntryEvent(String[] values) {
                System.out.println("Create event " + Arrays.asList(values).toString());
                try {
                    SlotEntity object = new SlotEntity(values[0], Integer.parseInt(values[1]));
                    return slotManager.addSlot(object);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    DialogUtil.showError(instance, "Ошибка подключения к бд при добавлении новой записи");
                    return null;
                }
            }
        };
        table.setModel(tableModel);

        sortButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.sortTable(new Comparator<SlotEntity>() {
                    @Override
                    public int compare(SlotEntity o1, SlotEntity o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
            }
        });

        sortButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.sortTable(new Comparator<SlotEntity>() {
                    @Override
                    public int compare(SlotEntity o1, SlotEntity o2) {
                        if(o1.getPrice() == o2.getPrice()) {
                            return 0;
                        }
                        return o1.getPrice() > o2.getPrice() ? 1 : -1;
                    }
                });
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.showCreateEntryForm();
            }
        });

        try {
            tableModel.addRows(slotManager.getAllSlots(), false);
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError("Ошибка при получении данных из бд!");
        }
    }

    @Override
    public int getFormWidth() {
        return 400;
    }

    @Override
    public int getFormHeight() {
        return 325;
    }
}
