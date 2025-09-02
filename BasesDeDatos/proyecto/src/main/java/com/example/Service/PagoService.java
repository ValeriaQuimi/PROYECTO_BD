package com.example.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class EntregaService {
    private static Scanner sc = new Scanner(System.in);

    public static void registrarEntrega() {
        try {
            System.out.println("\n=== REGISTRAR ENTREGA ===");

            System.out.print("Fecha de entrega (YYYY-MM-DD): ");
            String fechaStr = sc.nextLine();
            LocalDate fechaEntrega = LocalDate.parse(fechaStr);

            System.out.print("Estado de entrega (pendiente/entregada): ");
            String estadoEntrega = sc.nextLine().toLowerCase();

            if (!estadoEntrega.equals("pendiente") && !estadoEntrega.equals("entregada")) {
                System.out.println("Estado inválido. Debe ser 'pendiente' o 'entregada'.");
                return;
            }

            System.out.print("ID del repartidor: ");
            int idRepartidor = Integer.parseInt(sc.nextLine());

            System.out.print("Número de orden del pedido: ");
            int numOrden = Integer.parseInt(sc.nextLine());

            try (Connection conn = ConexionBD.getConnection();
                 CallableStatement cs = conn.prepareCall("{ CALL sp_insertar_entrega(?, ?, ?, ?) }")) {

                cs.setDate(1, Date.valueOf(fechaEntrega));
                cs.setString(2, estadoEntrega);
                cs.setInt(3, idRepartidor);
                cs.setInt(4, numOrden);

                cs.execute();
                System.out.println("Entrega registrada correctamente usando SP.");

            } catch (SQLException e) {
                System.out.println("Error al registrar entrega: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error general al registrar entrega: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error en la entrada de datos: " + e.getMessage());
        }
    }

    public static void actualizarEntrega() {
        try {
            System.out.println("\n=== ACTUALIZAR ENTREGA ===");

            System.out.print("Número de entrega a actualizar: ");
            int numEntrega = Integer.parseInt(sc.nextLine());

            System.out.print("Nuevo estado (pendiente/entregada): ");
            String nuevoEstado = sc.nextLine().toLowerCase();

            if (!nuevoEstado.equals("pendiente") && !nuevoEstado.equals("entregada")) {
                System.out.println("Estado inválido.");
                return;
            }

            System.out.print("Nueva fecha (YYYY-MM-DD): ");
            String nuevaFechaStr = sc.nextLine();
            LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr);

            System.out.print("Nuevo ID de repartidor: ");
            int nuevoRepartidor = Integer.parseInt(sc.nextLine());

            System.out.print("Nuevo número de pedido: ");
            int nuevoPedido = Integer.parseInt(sc.nextLine());

            try (Connection conn = ConexionBD.getConnection();
                 CallableStatement cs = conn.prepareCall("{ CALL sp_actualizar_entrega(?, ?, ?, ?, ?) }")) {

                cs.setInt(1, numEntrega);
                cs.setString(2, nuevoEstado);
                cs.setDate(3, Date.valueOf(nuevaFecha));
                cs.setInt(4, nuevoRepartidor);
                cs.setInt(5, nuevoPedido);

                cs.execute();
                System.out.println("Entrega actualizada correctamente.");

            } catch (SQLException e) {
                System.out.println("Error al actualizar entrega: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error general al actualizar entrega: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error en la entrada de datos: " + e.getMessage());
        }
    }

    public static void eliminarEntrega() {
        System.out.print("\nIngrese el número de entrega a eliminar: ");
        int numEntrega = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_eliminar_entrega(?) }")) {

            cs.setInt(1, numEntrega);
            cs.execute();
            System.out.println("Entrega eliminada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar entrega: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al eliminar entrega: " + e.getMessage());
        }
    }

    public static void consultarEntregas() {
        System.out.println("\n=== LISTA DE ENTREGAS ===");

        String sql = "SELECT e.numEntrega, e.fechaEntrega, e.estadoEntrega, " +
                     "r.nombre AS nombreRepartidor, p.numOrden " +
                     "FROM Entrega e " +
                     "JOIN Repartidor r ON e.idRepartidor = r.idRepartidor " +
                     "JOIN Pedido p ON e.numOrden = p.numOrden " +
                     "ORDER BY e.numEntrega";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            System.out.printf("%-8s %-12s %-15s %-20s %-10s\n", 
                             "Entrega", "Fecha", "Estado", "Repartidor", "Pedido");
            System.out.println("------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-8d %-12s %-15s %-20s %-10d\n",
                                rs.getInt("numEntrega"),
                                rs.getDate("fechaEntrega"),
                                rs.getString("estadoEntrega"),
                                rs.getString("nombreRepartidor"),
                                rs.getInt("numOrden"));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar entregas: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al consultar entregas: " + e.getMessage());
        }
    }
}
