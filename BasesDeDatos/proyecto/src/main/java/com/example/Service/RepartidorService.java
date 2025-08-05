package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.example.Entidades.Repartidor;

public class RepartidorService {

    private static Scanner sc = new Scanner(System.in);

    public static void registrarRepartidor() {
        System.out.println("\n=== REGISTRO DE REPARTDOR ===");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Teléfono: ");
        int telefono = Integer.parseInt(sc.nextLine());

        String sql = "INSERT INTO Repartidor (nombre, telefono) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, telefono);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Repartidor registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el repartidor.");
            }
        } catch (Exception e) {
            System.out.println("Error al registrar repartidor: " + e.getMessage());
        }
    }

    public static void actualizarRepartidor() {
        System.out.print("\nIngrese el ID del repartidor a actualizar: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo teléfono: ");
        int nuevoTelefono = Integer.parseInt(sc.nextLine());

        String sql = "UPDATE Repartidor SET telefono = ? WHERE idRepartidor = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuevoTelefono);
            ps.setInt(2, id);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Repartidor actualizado correctamente.");
            } else {
                System.out.println("Repartidor no encontrado o no se pudo actualizar.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar repartidor: " + e.getMessage());
        }
    }

    public static void eliminarRepartidor() {
        System.out.print("\nIngrese el ID del repartidor a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Repartidor WHERE idRepartidor = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Repartidor eliminado correctamente.");
            } else {
                System.out.println("Repartidor no encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar repartidor: " + e.getMessage());
        }
    }

    public static void consultarRepartidores() {
        System.out.println("\n--- Listado de Repartidores ---");

        String sql = "SELECT idRepartidor, nombre, telefono FROM Repartidor";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "ID: %d | Nombre: %s | Teléfono: %d \n",
                        rs.getInt("idRepartidor"),
                        rs.getString("nombre"),
                        rs.getInt("telefono"));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar repartidores: " + e.getMessage());
        }
    }

    public static Repartidor buscarRepartidorPorId(int id) {
        String sql = "SELECT idRepartidor, nombre, telefono FROM Repartidor WHERE idRepartidor = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idRepartidor= rs.getInt("idRepartidor");
                    String nombre= rs.getString("nombre");
                    int telefono= rs.getInt("telefono");

                    System.out.printf("ID: %d | Nombre: %s | Teléfono: %d\n", idRepartidor, nombre, telefono);

                } else {
                System.out.println("Repartidor no encontrado");
            }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar repartidor: " + e.getMessage());
        }

        return null; // No encontrado
    }

}
