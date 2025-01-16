package preparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertData {
    public static void insertIntoMySQLData(Connection connection, String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the id");
        preparedStatement.setInt(1, sc.nextInt());
        sc.nextLine();
        System.out.println("Enter the name");
        preparedStatement.setString(2, sc.nextLine());
        System.out.println("Enter the job_title");
        preparedStatement.setString(3, sc.nextLine());
        System.out.println("Enter the salary");
        preparedStatement.setDouble(4, sc.nextDouble());

//        preparedStatement.setInt(1, 4);
//        preparedStatement.setString(2, "Astha");
//        preparedStatement.setString(3, "Engineer");
//        preparedStatement.setDouble(4, 40000.0);

        // insert the data
        int rowsAffected = preparedStatement.executeUpdate();
        if(rowsAffected>0){
            System.out.println("Insert Successfully!! "+ rowsAffected+ " row(s) affected");
        }else{
            System.out.println("insertion failed!!");
        }
    }
}
