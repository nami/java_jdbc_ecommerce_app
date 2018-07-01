package MySQL.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;



// makes a method that allow for connection to the jdbc database
public class dbService {

    // the instance variables for connection
    private Connection connection = null;
    private Statement statement = null;

    /**
     * Returns standard JDBC Connection with preset connection string
     *
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception {

        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Setup the connection and pass the user, timezone & language parameters
        connection = DriverManager.getConnection("jdbc:mysql://localhost/ECommerce?" +
                "user=root&password=**********&useSSL=false&serverTimezone=UTC&characterEncoding=utf8");

        return connection;
    }

    /**
     * Returns standard standard JDBC Statement Object used to execute queries
     * @return
     * @throws Exception
     */
    public Statement getStatement() throws Exception {

        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Setup the connection and pass the user, timezone & language parameters
        connection = DriverManager.getConnection("jdbc:mysql://localhost/ECommerce?" +
                "user=root&password=*************&useSSL=false&serverTimezone=UTC&characterEncoding=utf8");

        // This is used to execute queries
        statement = connection.createStatement();

        return statement;
    }
}
