package transactionHandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionHandling {
    public static void main(String[] args) {
        //database url
        String url = "jdbc:mysql://localhost:3306/mydatabase";

        String username = "root";
        String password = "root";
        String withdrawQuery = "update accounts set balance = balance - ? where account_num =? ";
        String depositeQuery = "update accounts set balance = balance + ? where account_num =? ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println();
            System.out.println("Driver loaded successfully!!");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection establish successfully!!");
            connection.setAutoCommit(false);
            try {
                PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery);
                withdrawStatement.setDouble(1, 500.0);
                withdrawStatement.setString(2, "account456");

                PreparedStatement depositeStatement = connection.prepareStatement(depositeQuery);
                depositeStatement.setDouble(1, 500.0);
                depositeStatement.setString(2, "account123");

                int withdrawalAffectedRows = withdrawStatement.executeUpdate();
                int depositeAffectedRows = depositeStatement.executeUpdate();
                if (depositeAffectedRows > 0 && withdrawalAffectedRows > 0) {
                    connection.commit();
                    System.out.println("transaction successfully!!");
                } else {
                    connection.rollback();
                    System.out.println("transaction rollback");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
