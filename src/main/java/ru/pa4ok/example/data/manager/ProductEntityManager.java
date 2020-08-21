package ru.pa4ok.example.data.manager;

import ru.pa4ok.example.data.entity.ProductEntity;
import ru.pa4ok.library.data.mysql.BaseManager;
import ru.pa4ok.library.data.mysql.MysqlDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

/*
    Класс для работы с сущностями товаров из базы данных
 */
public class ProductEntityManager extends BaseManager
{
    private static final String PRODUCT_TABLE = "Product";

    public ProductEntityManager(MysqlDatabase database) {
        super(database);
    }

    public ProductEntity add(ProductEntity entity) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO " + PRODUCT_TABLE
                    + "(Title, Cost, Description, MainImagePath, IsActive, Manufacturer) "
                    + "values(?, ?, ?, ?, ?, ?)";

            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, entity.getTitle());
            s.setDouble(2, entity.getCost());
            s.setString(3, entity.getDescription());
            s.setString(4, entity.getImgPath());
            s.setInt(5, entity.isActive() ? 1 : 0);
            s.setString(6, entity.getManufacturer());

            s.executeUpdate();

            ResultSet resultSet = s.getGeneratedKeys();
            if(resultSet.next()) {
                entity.setId(resultSet.getInt(1));
                return entity;
            }

            return null;
        }
    }

    public int update(ProductEntity entity) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "UPDATE " + PRODUCT_TABLE + " SET Title=?, Cost=?, Description=?, MainImagePath=?, IsActive=?, Manufacturer=? WHERE ID=?";

            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, entity.getTitle());
            s.setDouble(2, entity.getCost());
            s.setString(3, entity.getDescription());
            s.setString(4, entity.getImgPath());
            s.setInt(5, entity.isActive() ? 1 : 0);
            s.setString(6, entity.getManufacturer());
            s.setInt(7, entity.getId());

            return s.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
            {
            String sql = "DELETE FROM " + PRODUCT_TABLE + " WHERE ID=?";

            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public ProductEntity getUserById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM " + PRODUCT_TABLE + " WHERE ID=?";

            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new ProductEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getDouble("Cost"),
                        resultSet.getString("Description"),
                        resultSet.getString("MainImagePath"),
                        resultSet.getInt("IsActive"),
                        resultSet.getString("Manufacturer")
                        );
            }
        }

        return null;
    }

    public List<ProductEntity> getAll() throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM " + PRODUCT_TABLE;
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<ProductEntity> entities = new ArrayList<>();
            while(resultSet.next()) {
                entities.add(new ProductEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getDouble("Cost"),
                        resultSet.getString("Description"),
                        resultSet.getString("MainImagePath"),
                        resultSet.getInt("IsActive"),
                        resultSet.getString("Manufacturer")
                ));
            }

            return entities;
        }
    }
}
