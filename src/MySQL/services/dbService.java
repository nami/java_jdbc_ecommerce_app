package MySQL.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class dbService {

    private Connection connection = null;
    private Statement statement = null;

    // Returns standard JDBC Connection with preset connection string

    public Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        // Setup the connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost/ECommerce?" +
                "user=root&password=******&useSSL=false&serverTimezone=UTC&characterEncoding=utf8");

        return connection;
    }

    // Returns standard standard JDBC Statement Object used to execute queries

    public Statement getStatement() throws Exception {

        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Setup the connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost/ECommerce?" +
                "user=root&password=trisana28&useSSL=false&serverTimezone=UTC&characterEncoding=utf8");

        // This is used to execute queries
        statement = connection.createStatement();

        return statement;
    }
}
