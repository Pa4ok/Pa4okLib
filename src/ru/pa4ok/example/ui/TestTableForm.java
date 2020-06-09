package ru.pa4ok.example.ui;

import ru.pa4ok.example.Main;
import ru.pa4ok.example.data.entity.SlotEntity;
import ru.pa4ok.example.data.manager.SlotEntityManager;
import ru.pa4ok.library.ui.dialog.DialogUtil;
import ru.pa4ok.library.ui.form.BaseForm;
import ru.pa4ok.library.ui.jtable.EditableTableModel;
import ru.pa4ok.library.ui.jtable.EditableTableHeader;
import ru.pa4ok.library.util.DataFilter;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.sql.SQLException;
import java.util.List;

public class TestTableForm extends BaseForm
{
    private JPanel mainPanel;
    private JTable table;

    private EditableTableModel<SlotEntity> tableModel;

    private SlotEntityManager slotManager = new SlotEntityManager(Main.getInstance().getDatabase());

    public TestTableForm()
    {
        setContentPane(mainPanel);

        tableModel = new EditableTableModel<SlotEntity>(table) {
            @Override
            public void getTableHeaders(List<EditableTableHeader> headers) {
                headers.add(new EditableTableHeader("id", false));
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
