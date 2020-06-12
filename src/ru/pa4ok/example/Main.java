package ru.pa4ok.example;

import ru.pa4ok.example.data.entity.SlotEntity;
import ru.pa4ok.example.ui.AddSlotForm;
import ru.pa4ok.example.ui.TestForm;
import ru.pa4ok.example.ui.TestTableForm;
import ru.pa4ok.library.data.MysqlDatabase;
import ru.pa4ok.example.data.manager.SlotEntityManager;
import ru.pa4ok.library.ui.form.BaseForm;

import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Random;

/**
 * Тут я показываю адекватную с точки зрения используемых средств работу и
 * модели ООП работу с базой данных, если Вам хочется ручками все открывать,
 * закрывать и восстанавливать и вы не хотите думать - закройте проект
 *
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

        //тестовый пример использования
        //на практике помещаете объект в private поле класса, где он будет нужен и используете
        //получить database можно через Main.getInstance().getDatabase()
        SlotEntityManager slotEntityManager = new SlotEntityManager(database);

        try {
            slotEntityManager.createSlotTable();
            //другие реализованные в классе методы

        } catch (SQLException exception) {
            exception.printStackTrace();
            //тут обрабатываете вашу ошибку, выводите варинги и тд
        }
        //никаких закрытый, открытий и восстановлений соединения делать не нужно
        //все делается за вас НОРМАЛЬНЫМИ средствами

        BaseForm.setBaseApplicationIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("icon.jpg")));

        /* тест шрифтов
        try {
            Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("lobster.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }*/

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
