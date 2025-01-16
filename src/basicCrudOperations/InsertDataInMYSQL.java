package basicCrudOperations;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertDataInMYSQL {
    public static void insertIntoMySQLData(Statement stmt, String query) throws SQLException {

        // insert the data
        int rowsAffected = stmt.executeUpdate(query);
        if(rowsAffected>0){
            System.out.println("Insert Successfully!! "+ rowsAffected+ " row(s) affected");
        }else{
            System.out.println("insertion failed!!");
        }
    }
}
