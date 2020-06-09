package ru.pa4ok.example.ui;

import ru.pa4ok.example.Main;
import ru.pa4ok.example.data.entity.SlotEntity;
import ru.pa4ok.example.data.manager.SlotEntityManager;
import ru.pa4ok.library.ui.form.BaseForm;
import ru.pa4ok.library.ui.jtable.DataTableModel;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.sql.SQLException;

public class TestForm extends BaseForm
{
    private JPanel mainPanel;
    private JTable table;

    private SlotEntityManager slotEntityManager = new SlotEntityManager(Main.getInstance().getDatabase());
    private DataTableModel<SlotEntity> slotTableModel;

    public TestForm()
    {
        setContentPane(mainPanel);

        slotTableModel = new DataTableModel<SlotEntity>(table)
        {
            @Override
            protected Object[] getTableHeaders() {
                return new Object[] { "id", "title", "price" };
            }

            @Override
            public SlotEntity getObjectFromData(Object[] data) {
                return new SlotEntity(Integer.parseInt((String)data[0]), (String)data[1], Integer.parseInt((String)data[2]));
            }

            @Override
            public String[] getRowDataFromObject(SlotEntity obj) {
                return new String[] { String.valueOf(obj.getId()), obj.getTitle(), String.valueOf(obj.getPrice()) };
            }
        };

        table.setModel(slotTableModel);

        try {
            slotTableModel.addRows(slotEntityManager.getAllSlots());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        System.out.println(slotTableModel.getTableContent());
    }

    @Override
    public int getFormWidth() {
        return 400;
    }

    @Override
    public int getFormHeight() {
        return 400;
    }
}
