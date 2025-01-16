package preparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteData {
    public static void deleteDataFromMySQL(Connection connection, String deleteQuery) throws SQLException {
        //Statement interface is used to run the retrieveQuery so this creates a statement
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id which you want to delete");
        preparedStatement.setInt(1, scanner.nextInt());
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("DELETION Successfully!! " + rowsAffected + " row(s) affected");
        } else {
            System.out.println("DELETION failed!!");
        }
    }
}
