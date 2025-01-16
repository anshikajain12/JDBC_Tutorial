import basicCrudOperations.CrudOperations;
import preparedStatement.CrudOperationsUsingPreparedStatement;

import java.util.Arrays;

public class Main {
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
            System.out.println(e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
        }
//        CrudOperations.performBasicCrudOperations(url, username, password);
        CrudOperationsUsingPreparedStatement.performCrudOperations(url, username, password);
    }
}