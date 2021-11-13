package ru.pa4ok.library.network.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Getter
@AllArgsConstructor
public class MysqlDatabase
{
    private final String serverName;
    private final String dbName;
    private final String user;
    private final String pass;
    private final int port;

    private DataSource source;

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
}
