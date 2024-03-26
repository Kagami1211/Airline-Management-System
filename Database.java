package AirlineManagementSystem;

import java.sql.*;
public class Database {

    private  String url = "jdbc:mysql://localhost/airline management system";
    private  String user = "user";
    private  String pass = "#1#2#3%1%2%3";
    private Statement statement;

    public Database() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
    }

    public Statement getStatement() {
        return statement;
    }
}
