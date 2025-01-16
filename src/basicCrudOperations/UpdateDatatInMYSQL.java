package basicCrudOperations;

import java.sql.SQLException;
import java.sql.Statement;

public class UpdateDatatInMYSQL {
    public static void updateDataInMySQL(Statement stmt, String deleteQuery) throws SQLException {
        int rowsAffected = stmt.executeUpdate(deleteQuery);
        if(rowsAffected>0){
            System.out.println("Updation Successfully!! "+ rowsAffected+ " row(s) affected");
        }else{
            System.out.println("Updation failed!!");
        }
    }
}
