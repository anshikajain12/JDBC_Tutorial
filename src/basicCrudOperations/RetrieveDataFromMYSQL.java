package basicCrudOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RetrieveDataFromMYSQL {
    public static ResultSet retrieveMySQLData(Statement stmt,ResultSet resultSet) throws SQLException {

//        // retrieve the query and modify it and store it in the resultSet
//        ResultSet resultSet = stmt.executeQuery(query);

        //perform db operations here
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String job_title = resultSet.getString("job_title");
            double salary = resultSet.getDouble("salary");
            System.out.println();
            System.out.println("===========================");
            System.out.println("ID: "+id);
            System.out.println("Name: "+name);
            System.out.println("Job_Title: "+job_title);
            System.out.println("Salary: "+salary);
        }
        return resultSet;
    }
}
