package basicCrudOperations;

import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDatatFromMYSQL {
    public static void deleteDataFromMySQL(Statement stmt, String deleteQuery) throws SQLException {
        int rowsAffected = stmt.executeUpdate(deleteQuery);
        if(rowsAffected>0){
            System.out.println("DELETION Successfully!! "+ rowsAffected+ " row(s) affected");
        }else{
            System.out.println("DELETION failed!!");
        }
    }
}
