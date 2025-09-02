package com.example.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RepartidorService {

    private static Scanner sc = new Scanner(System.in);

    public static void registrarRepartidor() {
        System.out.println("\n=== REGISTRO DE REPARTIDOR ===");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Teléfono: ");
        int telefono = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_insertarRepartidor(?, ?) }")) {

            cs.setString(1, nombre);
            cs.setInt(2, telefono);

            cs.execute();
            System.out.println("Repartidor registrado correctamente usando SP.");

        } catch (SQLException e) {
            System.out.println("Error al registrar repartidor: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al registrar repartidor: " + e.getMessage());
        }
    }

    public static void actualizarRepartidor() {
        System.out.print("\nIngrese el ID del repartidor a actualizar: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo teléfono: ");
        int nuevoTelefono = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_actualizarRepartidor(?, ?) }")) {

            cs.setInt(1, id);
            cs.setInt(2, nuevoTelefono);

            cs.execute();
            System.out.println("Repartidor actualizado correctamente usando SP.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar repartidor: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al actualizar repartidor: " + e.getMessage());
        }
    }

    public static void eliminarRepartidor() {
        System.out.print("\nIngrese el ID del repartidor a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_eliminarRepartidor(?) }")) {

            cs.setInt(1, id);
            cs.execute();
            System.out.println("Repartidor eliminado correctamente usando SP.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar repartidor: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al eliminar repartidor: " + e.getMessage());
        }
    }

    public static void consultarRepartidores() {
        System.out.println("\n--- Listado de Repartidores ---");

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_consultarRepartidores() }");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "ID: %d | Nombre: %s | Teléfono: %d \n",
                        rs.getInt("idRepartidor"),
                        rs.getString("nombre"),
                        rs.getInt("telefono"));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar repartidores: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al consultar repartidores: " + e.getMessage());
        }
    }

    public static void buscarRepartidorPorId(int id) {
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

    }

}
