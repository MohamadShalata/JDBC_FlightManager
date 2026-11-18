import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SimpleFlightUpdater {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/FlightDB?allowLoadLocalInfile=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jerusalem&useSSL=false";
        String user = "root";

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter MySQL root password: ");
            String password = scanner.nextLine();

            // Connect to the database
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Arrivals SET delay_status = ? WHERE flight_number = 'LX354'")) {

                // Prompt the user for a new delay status
                System.out.print("Type a new delay status for flight 'LX354': ");
                String userInput = scanner.nextLine();

                // Set the parameter value for the PreparedStatement
                preparedStatement.setString(1, userInput);
                // Execute the update using the PreparedStatement
                int rowsAffected = preparedStatement.executeUpdate();
                // Print the number of affected rows
                System.out.println("Number of affected rows: " + rowsAffected);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
