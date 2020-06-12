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
import java.util.Comparator;
import java.util.List;

public class TestTableForm extends BaseForm
{
    private static final Comparator<SlotEntity> byPrice = new Comparator<SlotEntity>() {
        @Override
        public int compare(SlotEntity o1, SlotEntity o2) {
            if(o1.getPrice() == o2.getPrice()) {
                return 0;
            }
            return o1.getPrice() > o2.getPrice() ? 1 : -1;
        }
    };

    private JPanel mainPanel;
    private JTable table;
    private JButton sortButton1;
    private JButton sortButton2;

    private EditableTableModel<SlotEntity> tableModel;

    private SlotEntityManager slotManager = new SlotEntityManager(Main.getInstance().getDatabase());

    public TestTableForm()
    {
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
                //тут должна быть синхронизация с базой данных и тд
                System.out.println("Change event " + row + " " + object);
            }

            @Override
            protected void onTableRemoveRowEvent(SlotEntity object) {
                //тут должна быть синхронизация с базой данных и тд
                System.out.println("Delete event " + object);
            }
        };
        table.setModel(tableModel);

        sortButton1.addActionListener(new ActionListener() {
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

        sortButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.sortTable(new Comparator<SlotEntity>() {
                    @Override
                    public int compare(SlotEntity o1, SlotEntity o2) {
                        if(o1.getPrice() == o2.getPrice()) {
                            return 0;
                        }
                        return o1.getPrice() < o2.getPrice() ? 1 : -1;
                    }
                });
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
