import java.sql.Connection;
import java.sql.DriverManager;

public class App {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/JDBC", "root", "120994");
            MenuEstudiantes menu = new MenuEstudiantes();
            menu.mostrarMenu(conn);
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}