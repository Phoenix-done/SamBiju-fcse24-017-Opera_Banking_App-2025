import java.sql.*;

public class SupabaseConnect {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://<host>:5432/<db>?sslmode=require";
        String user = "<user>";
        String password = "<password>";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOW()");
            while (rs.next()) {
                System.out.println("Connected! Server time: " + rs.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
