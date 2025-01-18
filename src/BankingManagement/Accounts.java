package BankingManagement;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public long open_account(String email) {
        if (!account_exist(email)) {
            String query = "Insert into accounts(account_number,full_name,email,balance,security_pin) values(?,?,?,?,?);";
            scanner.nextLine();
            System.out.println("Enter Full Name:");
            String fullName = scanner.nextLine();
            System.out.println("Enter Initial Amount:");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter security pin:");
            String security_pin = scanner.nextLine();
            try {
                long account_number = generateAccountNumber();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, security_pin);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Account open successfully!!");
                    return account_number;
                } else {
                    throw new RuntimeException("Account creation failed!!");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        throw new RuntimeException("Account Already Exist!!");
    }

    public long getAccount_Number(String email) {
        String query = "select account_number from accounts where email = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("account_number");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException("Account number doesn't exist!!");
    }

    private long generateAccountNumber() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select account_number from accounts order by account_number desc limit 1");
            if (resultSet.next()) {
                long last_account_number = resultSet.getLong("account_number");
                return last_account_number + 1;
            } else {
                return 100001000;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 100001000;
    }

    public boolean account_exist(String email) {
        String query = "select account_number from accounts where email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
