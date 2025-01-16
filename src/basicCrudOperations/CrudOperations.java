package basicCrudOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CrudOperations {
    public static void performBasicCrudOperations(String url, String username, String password) {
        String retrieveQuery = "select * from employee;";
        String insertQuery = "INSERT INTO employee(id,name,job_title,salary) values(3,'Anshika','Game Developer',90000.0)";
        String deleteQuery = "DELETE FROM employee WHERE id=2";
        String updateQuery = "UPDATE employee SET job_title='Senior Game Developer',salary=100000.0 WHERE id=3";

        try {
            //establish connection: first DriverManager fetch 1 driver and
            // then Connection interface creates a connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection establish successfully!! " + connection);
            //Statement interface is used to run the retrieveQuery so this creates a statement
            Statement stmt = connection.createStatement();

            ResultSet resultSet = stmt.executeQuery(retrieveQuery);

            //Retrieve data from MySQL
            RetrieveDataFromMYSQL.retrieveMySQLData(stmt, resultSet);

            //insert query
//            InsertDataInMYSQL.insertIntoMySQLData(stmt, insertQuery);

            //delete query
//            DeleteDatatFromMYSQL.deleteDataFromMySQL(stmt,deleteQuery);

            //update query
//            updateDataInMySQL(stmt, updateQuery);

            //close all the connections
            resultSet.close();
            stmt.close();
            connection.close();
            System.out.println();
            System.out.println("All the connection close successfully!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
