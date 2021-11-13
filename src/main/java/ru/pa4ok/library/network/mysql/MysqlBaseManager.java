package ru.pa4ok.library.network.mysql;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Getter
@AllArgsConstructor
public abstract class MysqlBaseManager
{
    protected final MysqlDatabase database;

    public int createTable(String tableName, String fields) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + fields + ");";
            Statement s = c.createStatement();

            return s.executeUpdate(sql);
        }
    }

    public int deleteTable(String tableName) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DROP TABLE IF EXISTS " + tableName + ";";
            Statement s = c.createStatement();

            return s.executeUpdate(sql);
        }
    }
}
