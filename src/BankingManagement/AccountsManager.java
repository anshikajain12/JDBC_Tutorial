package BankingManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountsManager {
    private Connection connection;
    private Scanner scanner;

    public AccountsManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void credit_money(long account_number) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter Amount");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security pin");
        String security_pin = scanner.nextLine();
        try {
            connection.setAutoCommit(false);
            if (account_number != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from accounts where account_number = ? and security_pin = ?;");
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (amount <= current_balance) {
                        String credit_query = "update accounts set balance = balance + ? where account_number = ?";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(credit_query);
                        preparedStatement1.setDouble(1, amount);
                        preparedStatement1.setLong(2, account_number);
                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Rs. " + amount + " Credit successfully!!");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction failed!!");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Invalid security pin!");
                    }
                } else {
                    System.out.println("Invalid security pin!");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connection.setAutoCommit(true);
    }

    public void debit_money(long account_number) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter Amount");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security pin");
        String security_pin = scanner.nextLine();
        try {
            connection.setAutoCommit(false);
            if (account_number != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from accounts where account_number = ? and security_pin = ?;");
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (amount <= current_balance) {
                        String debit_query = "update accounts set balance = balance - ? where account_number = ?";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(debit_query);
                        preparedStatement1.setDouble(1, amount);
                        preparedStatement1.setLong(2, account_number);
                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Rs. " + amount + " debited successfully!!");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction failed!!");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient balance ");
                    }
                } else {
                    System.out.println("Enter valid security pin!");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connection.setAutoCommit(true);
    }

    public void getBalance(long account_number) {
        scanner.nextLine();
        System.out.println("Enter security pin");
        String security_pin = scanner.nextLine();
        String query = "select balance from accounts where account_number = ? and security_pin= ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance is: " + balance + " in " + account_number + " account");
            } else {
                System.out.println("Invalid Pin!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transferMoney(long sener_account_number) {
        scanner.nextLine();
        System.out.println("Enter Reciever Account Number: ");
        long reciever_account_number = scanner.nextLong();
        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security pin");
        String security_pin = scanner.nextLine();
        try {
            connection.setAutoCommit(false);
            //TODO: check correct account number
            if (sener_account_number != 0 && reciever_account_number != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from accounts where account_number = ? and security_pin =? ");
                preparedStatement.setLong(1, sener_account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                //TODO: check correct security pin
                if (resultSet.next()) {
                    System.out.println("*********************************************");
                    double current_balance = resultSet.getDouble("balance");
                    //TODO: check amount should be less than or equal to balance
                    if (amount <= current_balance) {
                        String debit_query = "update accounts set balance = balance - ? where account_number = ?";
                        String credit_query = "update accounts set balance = balance + ? where account_number = ?";
                        //TODO: credit statement
                        PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);
                        creditPreparedStatement.setDouble(1, amount);
                        creditPreparedStatement.setLong(2, reciever_account_number);
                        //TODO: debit statement
                        PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);
                        debitPreparedStatement.setDouble(1, amount);
                        debitPreparedStatement.setLong(2, sener_account_number);

                        int rowsAffected1 = debitPreparedStatement.executeUpdate();
                        int rowsAffected2 = creditPreparedStatement.executeUpdate();
                        if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                            System.out.println("Transaction successful!!");
                            System.out.println("Rs. " + amount + " Transaction Successfully");
                            System.out.println("From " + sener_account_number + " to " + reciever_account_number);
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction failed!!");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient Balance!");
                    }
                } else {
                    System.out.println("Invalid security pin!!");
                }
            } else {
                System.out.println("Invalid Account number!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
