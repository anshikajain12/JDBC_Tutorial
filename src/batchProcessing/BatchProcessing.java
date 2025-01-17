package batchProcessing;

import java.sql.*;
import java.util.Scanner;

public class BatchProcessing {
    public static void main(String[] args) {
        //database url
        String url = "jdbc:mysql://localhost:3306/mydatabase";

        String username = "root";
        String password = "root";

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
            //TODO: using statement
//            Statement statement =connection.createStatement();
//            statement.addBatch("Insert into employee(name,job_title,salary)values('Vashu','HR Manager',65000.0)");
//            statement.addBatch("Insert into employee(name,job_title,salary)values('Karan','Cyber security Analyst',62000.0)");
//            statement.addBatch("Insert into employee(name,job_title,salary)values('Rachiel','DevOps Engineer',67000.0)");
//            int[] batchResult = statement.executeBatch();
//            connection.commit();
//            System.out.println("batch added successfully!!");

            //TODO: using preparedStatement
            String query = "Insert into employee(name,job_title,salary)values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            Scanner scan = new Scanner(System.in);
            boolean decission = true;
            while (decission) {
                System.out.println("Name: ");
                String name = scan.nextLine();
                System.out.println("Jb title: ");
                String job_title = scan.nextLine();
                System.out.println("Salary: ");
                double salary = scan.nextDouble();
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, job_title);
                preparedStatement.setDouble(3, salary);
                preparedStatement.addBatch();
                System.out.println("Add more values Y/N");
                String addMoreValues = scan.next();
                decission = addMoreValues.toUpperCase().equals("Y") ? true : false;
                scan.nextLine();
            }
            int[] batchResult = preparedStatement.executeBatch();
            connection.commit();
            System.out.println("batch added successfully!!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
