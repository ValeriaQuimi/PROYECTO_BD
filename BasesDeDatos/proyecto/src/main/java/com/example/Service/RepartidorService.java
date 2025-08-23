package com.example.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RepartidorService {

    private static Scanner sc = new Scanner(System.in);

    public static void registrarRepartidor() {
        System.out.println("\n=== REGISTRO DE REPARTDOR ===");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Teléfono: ");
        int telefono = Integer.parseInt(sc.nextLine());

        String sql = "{CALL sp_insertarRepartidor(?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombre);
            cs.setInt(2, telefono);

            cs.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void actualizarRepartidor() {
        System.out.print("\nIngrese el ID del repartidor a actualizar: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo teléfono: ");
        int nuevoTelefono = Integer.parseInt(sc.nextLine());

        String sql = "{CALL sp_actualizarRepartidor(?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setInt(2, nuevoTelefono);

            cs.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void eliminarRepartidor() {
        System.out.print("\nIngrese el ID del repartidor a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "{CALL sp_eliminar_repartidor(?)}";
        try (Connection conn = ConexionBD.getConnection();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);

            cs.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void consultarRepartidores() {
        System.out.println("\n--- Listado de Repartidores ---");

        String sql = "{CALL sp_consultarRepartidores()}";
        try (Connection conn = ConexionBD.getConnection();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "ID: %d | Nombre: %s | Teléfono: %d \n",
                        rs.getInt("idRepartidor"),
                        rs.getString("nombre"),
                        rs.getInt("telefono"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
