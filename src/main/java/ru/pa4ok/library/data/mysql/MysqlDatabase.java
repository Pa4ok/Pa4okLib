package ru.pa4ok.library.data.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDatabase
{
    private final String serverName;
    private final String dbName;
    private final String user;
    private final String pass;
    private final int port;

    private DataSource source;

    public MysqlDatabase(String serverName, String dbName, String user, String pass, int port)
    {
        this.serverName = serverName;
        this.dbName = dbName;
        this.user = user;
        this.pass = pass;
        this.port = port;
    }

    public synchronized Connection getConnection() throws SQLException
    {
        if(this.source == null)
        {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();

            mysqlDataSource.setServerName(this.serverName);
            mysqlDataSource.setDatabaseName(this.dbName);
            mysqlDataSource.setUser(this.user);
            mysqlDataSource.setPassword(this.pass);
            mysqlDataSource.setPort(this.port);

            mysqlDataSource.setCharacterEncoding("UTF-8");
            mysqlDataSource.setServerTimezone("UTC");

            this.source = mysqlDataSource;
        }

        return this.source.getConnection();
    }

    public String getServerName() {
        return serverName;
    }

    public String getDbName() {
        return dbName;
    }


    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public int getPort() {
        return port;
    }
}
