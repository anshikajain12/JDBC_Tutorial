package preparedStatement;

import java.sql.*;

public class RetrieveData {
    public static void retrieveMySQLData(Connection connection,String retrieveQuery) throws SQLException {

        //Statement interface is used to run the retrieveQuery so this creates a statement
        PreparedStatement preparedStatement = connection.prepareStatement(retrieveQuery);
        preparedStatement.setString(1, "Anshika");
        preparedStatement.setInt(2, 3);

        ResultSet resultSet = preparedStatement.executeQuery();

        //perform db operations here
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String job_title = resultSet.getString("job_title");
            double salary = resultSet.getDouble("salary");
            System.out.println();
            System.out.println("===========================");
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Job_Title: " + job_title);
            System.out.println("Salary: " + salary);
        }
        resultSet.close();
    }
}
