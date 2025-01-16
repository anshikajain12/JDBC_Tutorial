package preparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateData {
    public static void updateDataInMySQL(Connection connection, String updateQuery) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        Scanner scanner = new Scanner(System.in);

        // Get user input for each parameter
        System.out.println("Enter the updated job_title:");
        String jobTitle = scanner.nextLine();
        System.out.println("Enter the updated salary:");
        double salary = scanner.nextDouble();
        System.out.println("Enter the id of the record to update:");
        int id = scanner.nextInt();

        // Set values for placeholders in the query
        preparedStatement.setString(1, jobTitle); // job_title
        preparedStatement.setDouble(2, salary);  // salary
        preparedStatement.setInt(3, id);         // id

        // Execute the update query
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Update Successful! " + rowsAffected + " row(s) affected.");
        } else {
            System.out.println("Update failed! No rows affected.");
        }
    }
}
