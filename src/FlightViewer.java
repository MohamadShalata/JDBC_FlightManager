import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FlightViewer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter MySQL root password: ");
        String password = scanner.nextLine();

        String url = "jdbc:mysql://localhost:3306/FlightDB?allowLoadLocalInfile=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jerusalem&useSSL=false";
        String user = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.print("Enter target terminal number (2, 3, 4, or 5): ");
            String terminal = scanner.nextLine();

            String sql = "SELECT Arrivals.id, Arrivals.flight_number, FlightRoutes.from_city, Arrivals.scheduled, Arrivals.delay_status, Arrivals.terminal FROM Arrivals INNER JOIN FlightRoutes ON Arrivals.flight_number = FlightRoutes.flight_number WHERE Arrivals.terminal = ? ORDER BY Arrivals.scheduled ASC";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, terminal);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.printf("%d\t%s\t%s\t%s\t%s\t%d%n",
                                          rs.getInt("id"),
                                          rs.getString("flight_number"),
                                          rs.getString("from_city"),
                                          rs.getTime    ("scheduled"),
                                          rs.getString("delay_status"),
                                          rs.getInt("terminal"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
