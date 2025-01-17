import java.sql.*;
import java.util.Scanner;

public class HotelReservation {
    private static String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static String username = "root";
    private static String password = "root";

    public static void main(String[] args) {
        boolean start = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //load all drivers
        } catch (
                ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (start) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get room number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid Choice. Try again.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        System.out.println("Enter guest name:");
        String guestName = scanner.next();
        System.out.println("Enter room number:");
        int roomNumber = scanner.nextInt();
        System.out.println("Enter contact number:");
        String contactNumber = scanner.next();
        String query = "INSERT INTO reservations(guest_name , room_no , contact_no)" +
                "values('" + guestName + "'," + roomNumber + ",'" + contactNumber + "')";
        try (Statement statement = connection.createStatement()) {
            int affectedRows = statement.executeUpdate(query);
            if (affectedRows > 0) {
                System.out.println("Reservation Successfull!!");
            } else {
                System.out.println("reservation failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewReservation(Connection connection) {
        String query = "SELECT reservation_id,guest_name , room_no , contact_no,reservation_date from reservations;";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Current Reservations:");
            System.out.println("+------------------+----------------+--------------+--------------------+-------------------+");
            System.out.println("   Reservation ID  | Guest         |  Room Number  | Contact Number     | Reservation Date   ");
            System.out.println("+------------------+----------------+--------------+--------------------+-------------------+");
            while (rs.next()) {
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s  |", rs.getInt("reservation_id"), rs.getString("guest_name"), rs.getInt("room_no"), rs.getString("contact_no"), rs.getTimestamp("reservation_date"));
                System.out.println();
            }
            System.out.println("+------------------+----------------+--------------+--------------------+-------------------+");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter Reservation Id: ");
            int reservationId = scanner.nextInt();
            System.out.println("Enter guest name:");
            String guestName = scanner.next();
            String sql = "select room_no from reservations " +
                    "where reservation_id = " + reservationId +
                    " and guest_name = '" + guestName + "';";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    int roomNumber = rs.getInt("room_no");
                    System.out.println("Room number for reservation id: " + reservationId + " and guest " + guestName + " is: " + roomNumber);
                } else {
                    System.out.println("reservation not found for given Id and name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter reservation id to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();
            if (!reservationExist(connection, reservationId)) {
                System.out.println("Reservation not found for this id: ");
                return;
            }
            System.out.println("Enter new guest name: ");
            String newGuestName = scanner.nextLine();
            System.out.println("Enter new room number: ");
            int roomNumber = scanner.nextInt();
            System.out.println("Enter new contact : ");
            String contactNumber = scanner.next();

            String sql = "UPDATE reservations SET guest_name = '" + newGuestName + "', room_no = " +
                    roomNumber + ", contact_no = '" + contactNumber + "' WHERE reservation_id = " + reservationId;

            try (Statement stmt = connection.createStatement()) {
                int affected = stmt.executeUpdate(sql);
                if (affected > 0) {
                    System.out.println("Reservation update succesfully!!");
                } else {
                    System.out.println("Reservation update failed!!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExist(Connection connection, int reservationId) {
        try {
            String sql = "Select reservation_id from reservations where reservation_id= " + reservationId;
            try (Statement stmt = connection.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(sql);
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter reservation id to delete: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();
            if (!reservationExist(connection, reservationId)) {
                System.out.println("Reservation not found for this id: ");
                return;
            }

            String sql = "DELETE from  reservations where reservation_id= " + reservationId;
            try (Statement stmt = connection.createStatement()) {
                int affected = stmt.executeUpdate(sql);
                if (affected > 0) {
                    System.out.println("Reservation deleted succesfully!!");
                } else {
                    System.out.println("Reservation deletion failed!!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Existing system");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using hotel reservation system!!");
    }
}