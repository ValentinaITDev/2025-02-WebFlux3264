import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EstudianteServices {
    private Scanner scanner = new Scanner(System.in);

    public void insertarEstudiante(Connection conn) {
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            
            System.out.print("Correo: ");
            String correo = scanner.nextLine();
            
            System.out.print("Edad: ");
            int edad = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Estado civil (SOLTERO, CASADO, VIUDO, UNION_LIBRE, DIVORCIADO): ");
            String estadoCivil = scanner.nextLine().toUpperCase();

            String sql = "INSERT INTO estudiantes (nombre, apellido, correo, edad, estado_civil) VALUES (?, ?, ?, ?, ?)";
            var stm = conn.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.setString(2, apellido);
            stm.setString(3, correo);
            stm.setInt(4, edad);
            stm.setString(5, estadoCivil);

            int rs = stm.executeUpdate();
            if (rs > 0) {
                System.out.println("Estudiante insertado correctamente");
            } else {
                System.out.println("Error al insertar estudiante");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Error: El correo ya existe");
            } else {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void actualizarEstudiante(Connection conn) {
        try {
            System.out.print("Correo del estudiante a actualizar: ");
            String correo = scanner.nextLine();

            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Nuevo apellido: ");
            String apellido = scanner.nextLine();
            
            System.out.print("Nueva edad: ");
            int edad = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Nuevo estado civil: ");
            String estadoCivil = scanner.nextLine().toUpperCase();

            String sql = "UPDATE estudiantes SET nombre=?, apellido=?, edad=?, estado_civil=? WHERE correo=?";
            var stm = conn.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.setString(2, apellido);
            stm.setInt(3, edad);
            stm.setString(4, estadoCivil);
            stm.setString(5, correo);

            int rs = stm.executeUpdate();
            if (rs > 0) {
                System.out.println("Estudiante actualizado correctamente");
            } else {
                System.out.println("No se encontro estudiante con ese correo");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarEstudiante(Connection conn) {
        try {
            System.out.print("Correo del estudiante a eliminar: ");
            String correo = scanner.nextLine();

            String sql = "DELETE FROM estudiantes WHERE correo=?";
            var stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            int rs = stm.executeUpdate();
            if (rs > 0) {
                System.out.println("Estudiante eliminado correctamente");
            } else {
                System.out.println("No se encontro estudiante con ese correo");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void consultarTodosLosEstudiantes(Connection conn) {
        try {
            String sql = "SELECT * FROM estudiantes ORDER BY id";
            var stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            System.out.println("\n=== LISTA DE ESTUDIANTES ===");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                int edad = rs.getInt("edad");
                String estadoCivil = rs.getString("estado_civil");

                System.out.println("ID: " + id + " | Nombre: " + nombre + " " + apellido + 
                                 " | Correo: " + correo + " | Edad: " + edad + " | Estado: " + estadoCivil);
            }
            System.out.println("=============================");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void consultarEstudiantePorEmail(Connection conn) {
        try {
            System.out.print("Correo del estudiante: ");
            String correo = scanner.nextLine();

            String sql = "SELECT * FROM estudiantes WHERE correo=?";
            var stm = conn.prepareStatement(sql);
            stm.setString(1, correo);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                System.out.println("\n=== ESTUDIANTE ENCONTRADO ===");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Apellido: " + rs.getString("apellido"));
                System.out.println("Correo: " + rs.getString("correo"));
                System.out.println("Edad: " + rs.getInt("edad"));
                System.out.println("Estado Civil: " + rs.getString("estado_civil"));
                System.out.println("=============================");
            } else {
                System.out.println("No se encontro estudiante con ese correo");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}