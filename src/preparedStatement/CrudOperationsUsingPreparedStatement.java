package preparedStatement;


import java.sql.*;

public class CrudOperationsUsingPreparedStatement {
    public static void performCrudOperations(String url, String username, String password) {
        String retrieveQuery = "select * from employee where name = ? and id = ?;";
        String insertQuery = "INSERT INTO employee(id, name, job_title, salary) VALUES(?, ?, ?, ?);";
        String deleteQuery = "DELETE FROM employee WHERE id = ?;";
        String updateQuery = "UPDATE employee SET job_title = ?, salary = ? WHERE id = ?;";

        try {
            //establish connection: first DriverManager fetch 1 driver and
            // then Connection interface creates a connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection establish successfully!! " + connection);

            //Retrieve data from MySQL
            RetrieveData.retrieveMySQLData(connection, retrieveQuery);
//
//            //insert query
//            InsertData.insertIntoMySQLData(connection, insertQuery);

            //delete query
//            DeleteData.deleteDataFromMySQL(connection, deleteQuery);

            //update query
//            UpdateData.updateDataInMySQL(connection, updateQuery);

            //close all the connections
            connection.close();
            System.out.println();
            System.out.println("All the connection close successfully!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
