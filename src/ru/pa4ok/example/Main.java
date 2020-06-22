package ru.pa4ok.example;

import ru.pa4ok.example.ui.TestTableForm;
import ru.pa4ok.library.data.MysqlDatabase;
import ru.pa4ok.library.ui.DialogUtil;
import ru.pa4ok.library.ui.FontUtil;
import ru.pa4ok.library.ui.form.BaseForm;
import ru.pa4ok.library.util.ResourceUtil;

import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * если кому то нужна помощь с демо экзаменом - пишите мне
 * https://vk.com/myfacekolya
 */

public class Main
{
    private static Main instance;

    private MysqlDatabase database;

    public Main()
    {
        instance = this;

        initDatabase();
        initUi();

        new TestTableForm();
    }

    private void initDatabase()
    {
        database = new MysqlDatabase("nleontnr.beget.tech", "nleontnr_exam", "nleontnr_exam", "yOCqf4MyOCqf", 3306);

        try(Connection c = database.getConnection()) {
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError("Ошибка подключения к базе данных");
            System.exit(-1);
        }
    }

    private void initUi()
    {
        FontUtil.changeAllFonts(new FontUIResource("Arial", Font.TRUETYPE_FONT, 12));
        BaseForm.setBaseApplicationTitle("Салон красоты Бровушка");
        try {
            BaseForm.setBaseApplicationIcon(ResourceUtil.getImage("beauty_logo.png"));
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showError("Не удалось загрузить иконку приложения");
        }
    }

    public MysqlDatabase getDatabase() {
        return database;
    }

    public static Main getInstance() {
        return instance;
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
