import java.sql.Connection;
import java.util.Scanner;

public class MenuEstudiantes {
    private Scanner scanner = new Scanner(System.in);
    private EstudianteServices service = new EstudianteServices();
    
    public void mostrarMenu(Connection conn) {
        int opcion = 0;
        
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Insertar Estudiante");
            System.out.println("2. Actualizar Estudiante");
            System.out.println("3. Eliminar Estudiante");
            System.out.println("4. Consultar todos los estudiantes");
            System.out.println("5. Consultar Estudiante por email");
            System.out.println("6. Salir del programa");
            System.out.print("Opcion: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1:
                        service.insertarEstudiante(conn);
                        break;
                    case 2:
                        service.actualizarEstudiante(conn);
                        break;
                    case 3:
                        service.eliminarEstudiante(conn);
                        break;
                    case 4:
                        service.consultarTodosLosEstudiantes(conn);
                        break;
                    case 5:
                        service.consultarEstudiantePorEmail(conn);
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        } while (opcion != 6);
    }
}