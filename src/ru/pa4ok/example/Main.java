package ru.pa4ok.example;

import ru.pa4ok.example.data.entity.SlotEntity;
import ru.pa4ok.example.ui.AddSlotForm;
import ru.pa4ok.example.ui.TestForm;
import ru.pa4ok.example.ui.TestTableForm;
import ru.pa4ok.library.data.MysqlDatabase;
import ru.pa4ok.example.data.manager.SlotEntityManager;
import ru.pa4ok.library.ui.FontUtil;
import ru.pa4ok.library.ui.form.BaseForm;
import ru.pa4ok.library.util.ResourceUtil;

import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

/**
 * если кому то нужна помощь с демо экзаменом - пишите мне
 * https://vk.com/myfacekolya
 */

public class Main
{
    private static Main instance;

    private final MysqlDatabase database = new MysqlDatabase("nleontnr.beget.tech", "nleontnr_exam", "nleontnr_exam", "yOCqf4MyOCqf", 3306);

    public Main()
    {
        instance = this;

        SlotEntityManager slotEntityManager = new SlotEntityManager(database);

        try {
            slotEntityManager.createSlotTable();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        FontUtil.changeAllFonts(new FontUIResource("Arial", Font.TRUETYPE_FONT, 12));
        try {
            BaseForm.setBaseApplicationIcon(ResourceUtil.getImage("icon.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddSlotForm form = new AddSlotForm();
        form.setVisible(true);
    }

    public MysqlDatabase getDatabase() {
        return database;
    }

    public static Main getInstance() {
        return instance;
    }
    public static void main(String[] args) {
        new Main();
    }
}
