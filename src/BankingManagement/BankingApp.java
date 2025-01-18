package BankingManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
    //        database url
    private static final String url = "jdbc:mysql://localhost:3306/banking_db";

    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) throws ClassNotFoundException, SQLClientInfoException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println();
            System.out.println("Driver loaded successfully!!");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scan = new Scanner(System.in);
            User user = new User(connection, scan);
            Accounts accounts = new Accounts(connection, scan);
            AccountsManager accountsManager = new AccountsManager(connection, scan);
            String email;
            long account_number = 0;
            while (true) {
                System.out.println("*** WELCOME TO BANKING SYSTEM ***");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("Enter your choice: ");
                int choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        email = user.login();
                        checkUserLoggedInOrNot(email, accounts, scan, account_number, accountsManager);
                        break;
                    case 3:
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!");
                        System.out.println("Exiting System!");
                        return;
                    default:
                        System.out.println("Enter Valid Choice");
                        break;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkUserLoggedInOrNot(String email, Accounts accounts, Scanner scan, long account_number, AccountsManager accountsManager) throws SQLException {
        if (email != null) {
            System.out.println();
            System.out.println("User Logged in");
            if (!accounts.account_exist(email)) {
                System.out.println();
                System.out.println("1. Open a new Bank Account");
                System.out.println("2. Exit");
                if (scan.nextInt() == 1) {
                    account_number = accounts.open_account(email);
                    System.out.println("Account Created Successfully!!");
                    System.out.println("Your Account Number is: " + account_number);
                } else {
                    return;
                }
            }
            account_number = accounts.getAccount_Number(email);
            int choice2 = 0;
            while (choice2 != 5) {
                System.out.println();
                System.out.println("1. Debit Money");
                System.out.println("2. Credit Money");
                System.out.println("3. Transfer Money");
                System.out.println("4. Check Balance");
                System.out.println("5. Logout");
                System.out.println("Enter Valid choice");
                choice2 = scan.nextInt();
                switch (choice2) {
                    case 1:
                        accountsManager.debit_money(account_number);
                        break;
                    case 2:
                        accountsManager.credit_money(account_number);
                        break;
                    case 3:
                        accountsManager.transferMoney(account_number);
                        break;
                    case 4:
                        accountsManager.getBalance(account_number);
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Enter valid choice!");
                        break;
                }
            }
        } else {
            System.out.println("Invalid Email or Password!");
        }

    }
}
