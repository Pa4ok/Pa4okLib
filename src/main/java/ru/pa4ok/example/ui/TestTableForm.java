package ru.pa4ok.example.ui;


import ru.pa4ok.example.Application;
import ru.pa4ok.example.data.entity.ProductEntity;
import ru.pa4ok.example.data.manager.ProductEntityManager;
import ru.pa4ok.library.swing.DialogUtil;
import ru.pa4ok.library.swing.form.BaseForm;
import ru.pa4ok.library.swing.jtable.EditableTableHeader;
import ru.pa4ok.library.swing.jtable.EditableTableModel;
import ru.pa4ok.library.util.DataFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
    Класс главного окна приложения
 */
public class TestTableForm extends BaseForm
{
    private final TestTableForm instance;

    private JPanel mainPanel;
    private JTable table;
    private JButton sort1Button;
    private JButton sort2Button;
    private JButton refreshButton;
    private JComboBox searchComboBox;
    private JButton addButton;
    private JLabel maxRowCountField;
    private JLabel currentRowCountField;
    private JButton helpButton;
    private JButton exitButton;

    private ProductEntityManager entityManager = new ProductEntityManager(Application.getInstance().getDatabase());
    private EditableTableModel<ProductEntity> editableTableModel;
    private List<String> manufacturers = new ArrayList<>();

    public TestTableForm()
    {
        instance = this;
        setContentPane(mainPanel);

        initTable();
        initSortElements();
        initButtons();

        refillTable();
        initSearchElements();

        setVisible(true);
    }

    private void initTable()
    {
        editableTableModel = new EditableTableModel<ProductEntity>(table)
        {
            @Override
            protected void getTableHeaders(List<EditableTableHeader> headers)
            {
                headers.add(new EditableTableHeader("ID", true, false));
                headers.add(new EditableTableHeader("Название", DataFilter.getBoundStringFilter(99)));
                headers.add(new EditableTableHeader("Цена", DataFilter.positiveDoubleFilter));
                headers.add(new EditableTableHeader("Описание", DataFilter.getBoundStringFilter(1024)));
                headers.add(new EditableTableHeader("Путь до изображения", DataFilter.getBoundStringFilter(999)));
                headers.add(new EditableTableHeader("Активен?", DataFilter.booleanFilter));
                headers.add(new EditableTableHeader("Производитель", DataFilter.getBoundStringFilter(99)));
            }

            @Override
            protected void onTableChangeEvent(int row, ProductEntity object) {
                System.out.println("Change event " + object);
                try {
                    entityManager.update(object);
                } catch (SQLException e) {
                    e.printStackTrace();
                    DialogUtil.showError("Ошибка при изменении записи в базе данных");
                }
            }

            @Override
            protected void onTableRemoveRowEvent(ProductEntity object) {
                System.out.println("Remove event " + object);
                try {
                    entityManager.deleteById(object.getId());
                    updateRowCountFields();
                } catch (SQLException e) {
                    e.printStackTrace();
                    DialogUtil.showError("Ошибка при удалении записи из базы данных");
                }
            }

            @Override
            protected ProductEntity onTableCreateEntryEvent(String[] values)
            {
                try {
                    ProductEntity newEntity = entityManager.add(new ProductEntity(
                            -1,
                            values[0],
                            Double.parseDouble(values[1]),
                            values[2],
                            values[3],
                            Boolean.parseBoolean(values[4]) ? 1 : 0,
                            values[5]
                    ));
                    System.out.println("Create event " + newEntity);
                    return newEntity;
                } catch (SQLException e) {
                    e.printStackTrace();
                    DialogUtil.showError("Ошибка при добавлении новой записи в базу данных");
                    return null;
                }
            }

            @Override
            protected ProductEntity getObjectFromData(String[] data) {
                return new ProductEntity(
                        Integer.parseInt(data[0]),
                        data[1],
                        Double.parseDouble(data[2]),
                        data[3],
                        data[4],
                        Boolean.parseBoolean(data[5]) ? 1 : 0,
                        data[6]
                );
            }

            @Override
            protected String[] getRowDataFromObject(ProductEntity obj) {
                return new String[] {
                        String.valueOf(obj.getId()),
                        obj.getTitle(),
                        String.valueOf(obj.getCost()),
                        obj.getDescription() == null ? "Описание товара" : obj.getDescription(),
                        obj.getImgPath(),
                        String.valueOf(obj.isActive()),
                        obj.getManufacturer()
                };
            }

            @Override
            public void showCreateEntryForm() {
                super.showCreateEntryForm();
                updateRowCountFields();
            }
        };
        table.setModel(editableTableModel);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ProductEntity product = editableTableModel.getRowObject(row);
                c.setBackground(product.isActive() ?  Color.WHITE : Color.LIGHT_GRAY);
                return c;
            }
        });
    }

    private void initSortElements()
    {
        sort1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editableTableModel.sortTable(new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Double.compare(o1.getCost(), o2.getCost());
                    }
                });
            }
        });

        sort2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editableTableModel.sortTable(new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Double.compare(o2.getCost(), o1.getCost());
                    }
                });
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refillTable();
                refillSearchComboBox();;
            }
        });
    }

    private void initSearchElements()
    {
        refillSearchComboBox();

        searchComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                refillTable();
            }
        });
    }

    private void refillSearchComboBox()
    {
        searchComboBox.removeAllItems();
        manufacturers.clear();

        searchComboBox.addItem("Выберите производителя");
        for(ProductEntity e : editableTableModel.getTableContent()) {
            if(!manufacturers.contains(e.getManufacturer())) {
                searchComboBox.addItem(e.getManufacturer());
                manufacturers.add(e.getManufacturer());
            }
        }
    }

    private void updateCurrentField()
    {
        currentRowCountField.setText(String.valueOf(editableTableModel.getTableContent().size()));
    }

    private void updateMaxField(int max)
    {
        maxRowCountField.setText(String.valueOf(max));
    }

    private void updateMaxField()
    {
        try {
            maxRowCountField.setText(String.valueOf(entityManager.getAll().size()));
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError("Ошибка при получении товаров из базы данных");
            maxRowCountField.setText("[ERROR]");
        }

    }

    private void updateRowCountFields()
    {
        updateCurrentField();
        updateMaxField();
    }

    private void initButtons()
    {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editableTableModel.showCreateEntryForm();
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogUtil.showInfo(instance, "Связаться с разработчиком можно по эл.почте - student17@exam.com");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DialogUtil.showConfirm("Вы точно хотите выйти из приложения?")) {
                    dispose();
                }
            }
        });
    }

    private void refillTable()
    {
        try {
            List<ProductEntity> entities = entityManager.getAll();
            updateMaxField(entities.size());
            if(searchComboBox.getSelectedItem() != null && searchComboBox.getSelectedIndex() != 0)
            {
                for(int i=entities.size()-1; i>=0; i--)
                {
                    ProductEntity e = entities.get(i);
                    if(!((String)searchComboBox.getSelectedItem()).equals(e.getManufacturer())) {
                        entities.remove(i);
                    }
                }
            }
            editableTableModel.clearTableContent(false);
            editableTableModel.addRows(entities, false);
            updateCurrentField();

        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError("Ошибка при получении товаров из базы данных");
        }
    }

    @Override
    public int getFormWidth() {
        return 700;
    }

    @Override
    public int getFormHeight() {
        return 650;
    }
}
