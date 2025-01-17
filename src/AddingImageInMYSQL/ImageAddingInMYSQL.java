package AddingImageInMYSQL;

import java.io.*;
import java.sql.*;

public class ImageAddingInMYSQL {
    public static void main(String[] args) throws ClassNotFoundException {
        //database url
        String url = "jdbc:mysql://localhost:3306/mydatabase";

        String username = "root";
        String password = "root";
        String image_path = "src\\AddingImageInMYSQL\\beach_image.jpg";
        String query = "Insert into image_table(image_data) values(?)";

        String folder_path = "src\\AddingImageInMYSQL\\imagemoveIn\\";
        String retrieveQuery = "select image_data from image_table where image_id = (?)";
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

            //TODO: add image in the image_table
//            FileInputStream fileInputStream = new FileInputStream(image_path); //convert it into binary format
//            byte[] imageData = new byte[fileInputStream.available()];
//            fileInputStream.read(imageData);
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setBytes(1, imageData);
//
//            int affectRows = preparedStatement.executeUpdate();
//            if (affectRows > 0) {
//                System.out.println("Image inserted successfully!!");
//            } else {
//                System.out.println("Image insertion failed!!");
//            }

            //TODO: retrieve image from image_table
            PreparedStatement preparedStatement = connection.prepareStatement(retrieveQuery);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("image_data");
                String extracted_image_name = folder_path + "extractedImage.jpg";
                OutputStream outputStream = new FileOutputStream(extracted_image_name);
                outputStream.write(imageData);
                System.out.println("Image extracted in the imagemoveIn folder");
            } else {
                System.out.println("Image not found!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
