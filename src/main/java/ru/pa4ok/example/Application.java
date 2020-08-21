package ru.pa4ok.example;

import ru.pa4ok.example.ui.TestTableForm;
import ru.pa4ok.library.data.MysqlDatabase;
import ru.pa4ok.library.swing.DialogUtil;
import ru.pa4ok.library.swing.FontUtil;
import ru.pa4ok.library.swing.form.BaseForm;
import ru.pa4ok.library.util.ResourceUtil;

import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;

public class Application
{
    private static Application instance;

    private MysqlDatabase database;

    public Application()
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

    public static Application getInstance() {
        return instance;
    }

    public static void main(String[] args)
    {
        new Application();
    }
}
