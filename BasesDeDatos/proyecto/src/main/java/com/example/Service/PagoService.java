package com.example.Service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class PagoService {
    private static Scanner sc = new Scanner(System.in);

    public static void gestionPagos() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PAGOS ---");
            System.out.println("1. Registrar nuevo pago");
            System.out.println("2. Eliminar pago");
            System.out.println("3. Consultar pagos");
            System.out.println("4. Editar pago");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarPago();
                    break;
                case 2:
                    eliminarPago();
                    break;
                case 3:
                    consultarPagos();
                    break;
                case 4:
                    editarPago();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void registrarPago() {
        try {
            System.out.println("\n=== REGISTRAR PAGO ===");

            System.out.print("Monto del pago: ");
            double monto = Double.parseDouble(sc.nextLine());

            System.out.print("Método de pago (efectivo/transferencia): ");
            String metodo = sc.nextLine().toLowerCase();

            if (!metodo.equals("efectivo") && !metodo.equals("transferencia")) {
                System.out.println("Método inválido. Debe ser 'efectivo' o 'transferencia'.");
                return;
            }

            System.out.print("Número de orden del pedido: ");
            int numOrden = Integer.parseInt(sc.nextLine());

            if (!existePedido(numOrden)) {
                System.out.println("Pedido no encontrado. No se puede registrar el pago.");
                return;
            }

            LocalDate fechaActual = LocalDate.now();

            String sql = "INSERT INTO Pago (fechaPago, montoPago, metodoPago, numOrden) VALUES (?, ?, ?, ?)";

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setDate(1, Date.valueOf(fechaActual));
                ps.setDouble(2, monto);
                ps.setString(3, metodo);
                ps.setInt(4, numOrden);

                int resultado = ps.executeUpdate();

                if (resultado > 0) {
                    System.out.println("Pago registrado correctamente.");
                } else {
                    System.out.println("No se pudo registrar el pago.");
                }

            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: formato numérico inválido.");
        }
    }

    public static void eliminarPago() {
        System.out.print("\nIngrese el ID del pago a eliminar: ");
        int idPago = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Pago WHERE idPago = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPago);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Pago eliminado correctamente.");
            } else {
                System.out.println("No se encontró un pago con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el pago: " + e.getMessage());
        }
    }

    public static void consultarPagos() {
        System.out.println("\n=== LISTA DE PAGOS ===");

        String sql = "SELECT idPago, fechaPago, montoPago, metodoPago, numOrden FROM Pago";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idPago");
                Date fecha = rs.getDate("fechaPago");
                double monto = rs.getDouble("montoPago");
                String metodo = rs.getString("metodoPago");
                int orden = rs.getInt("numOrden");

                System.out.printf("ID: %d | Fecha: %s | Monto: %.2f | Método: %s | Pedido Nº: %d\n",
                        id, fecha.toString(), monto, metodo, orden);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar pagos: " + e.getMessage());
        }
    }

    public static void editarPago() {
    System.out.print("\nIngrese el ID del pago a editar: ");
    int idPago = Integer.parseInt(sc.nextLine());

    String verificarSql = "SELECT * FROM Pago WHERE idPago = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement verificarPs = conn.prepareStatement(verificarSql)) {

        verificarPs.setInt(1, idPago);
        try (ResultSet rs = verificarPs.executeQuery()) {
            if (!rs.next()) {
                System.out.println("Pago no encontrado.");
                return;
            }
        }

        System.out.print("Nuevo monto: ");
        double nuevoMonto = Double.parseDouble(sc.nextLine());

        System.out.print("Nuevo método de pago (efectivo/transferencia): ");
        String nuevoMetodo = sc.nextLine().toLowerCase();

        if (!nuevoMetodo.equals("efectivo") && !nuevoMetodo.equals("transferencia")) {
            System.out.println("Método inválido.");
            return;
        }

        System.out.print("Nuevo número de orden (pedido): ");
        int nuevoPedido = Integer.parseInt(sc.nextLine());

        if (!existePedido(nuevoPedido)) {
            System.out.println("El número de pedido no existe.");
            return;
        }

        // Actualizar en la base de datos
        String updateSql = "UPDATE Pago SET montoPago = ?, metodoPago = ?, numOrden = ? WHERE idPago = ?";
        try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
            updatePs.setDouble(1, nuevoMonto);
            updatePs.setString(2, nuevoMetodo);
            updatePs.setInt(3, nuevoPedido);
            updatePs.setInt(4, idPago);

            int filas = updatePs.executeUpdate();
            if (filas > 0) {
                System.out.println("Pago actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el pago.");
            }

        }

    } catch (SQLException e) {
        System.out.println("Error al editar pago: " + e.getMessage());
    }
}


    // Método auxiliar para validar que el pedido exista
    private static boolean existePedido(int numOrden) {
        String sql = "SELECT numOrden FROM Pedido WHERE numOrden = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numOrden);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true si existe
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el pedido: " + e.getMessage());
            return false;
        }
    }
}
