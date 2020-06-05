package ru.pa4ok.example.data.manager;

import ru.pa4ok.library.data.BaseManager;
import ru.pa4ok.library.data.MysqlDatabase;
import ru.pa4ok.example.data.entity.SlotEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlotEntityManager extends BaseManager
{
    private static final String SLOTS_TABLE = "slots";

    public SlotEntityManager(MysqlDatabase database)
    {
        super(database);
    }

    public int createSlotTable() throws SQLException
    {
        return this.createTable(SLOTS_TABLE, "id INT(64) NOT NULL AUTO_INCREMENT, title VARCHAR(45), price INT(64), PRIMARY KEY(id)");
    }

    public int deleteSlotTable() throws SQLException
    {
        return this.deleteTable(SLOTS_TABLE);
    }

    public SlotEntity addSlot(SlotEntity slot) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO " + SLOTS_TABLE + "(title, price) values(?, ?)";

            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, slot.getTitle());
            s.setInt(2, slot.getPrice());
            s.executeUpdate();

            ResultSet resultSet = s.getGeneratedKeys();
            if(resultSet.next()) {
                slot.setId(resultSet.getInt(1));
                return slot;
            }

            return null;
        }
    }

    public int deleteSlotById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DELETE FROM " + SLOTS_TABLE + " WHERE id=?";

            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public int updateSlot(SlotEntity slot) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "UPDATE " + SLOTS_TABLE + " SET title=?, price=? WHERE id=?";

            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, slot.getTitle());
            s.setInt(2, slot.getPrice());
            s.setInt(3, slot.getId());

            return s.executeUpdate();
        }
    }

    public SlotEntity getSlotById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM " + SLOTS_TABLE + " WHERE id=?";

            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new SlotEntity(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getInt("price"));
            }
        }

        return null;
    }

    public List<SlotEntity> getSlotsByPrice(int price) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM " + SLOTS_TABLE + " WHERE price=?";

            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, price);

            List<SlotEntity> slots = new ArrayList<>();

            ResultSet resultSet = s.executeQuery();
            while(resultSet.next()) {
                slots.add(new SlotEntity(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getInt("price")));
            }

            return slots;
        }
    }

    public List<SlotEntity> getAllSlots() throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM " + SLOTS_TABLE;
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<SlotEntity> slots = new ArrayList<>();
            while(resultSet.next()) {
                slots.add(new SlotEntity(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getInt("price")));
            }

            return slots;
        }
    }
}
