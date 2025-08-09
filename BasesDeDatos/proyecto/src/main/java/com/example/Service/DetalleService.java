package com.example.Service;

import java.sql.*;
import java.util.Scanner;

public class DetalleService {
    private static final Scanner sc = new Scanner(System.in);

    public static void registrarDetalle() {
        try {
            System.out.println("\n=== REGISTRAR DETALLE ===");

            System.out.print("Número de orden: ");
            int numOrden = Integer.parseInt(sc.nextLine());

            System.out.print("ID del producto: ");
            int idProduct = Integer.parseInt(sc.nextLine());

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(sc.nextLine());

            System.out.print("Precio por cantidad: ");
            double precioCantidad = Double.parseDouble(sc.nextLine());

            String sql = "INSERT INTO Detalle (numOrden, idProduct, cantidad, precioCantidad) VALUES (?, ?, ?, ?)";

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, numOrden);
                ps.setInt(2, idProduct);
                ps.setInt(3, cantidad);
                ps.setDouble(4, precioCantidad);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println(" Detalle registrado correctamente.");
                } else {
                    System.out.println(" No se pudo registrar el detalle.");
                }

            } catch (SQLException e) {
                System.out.println(" Error en la base de datos: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println(" Error en el formato de los datos ingresados.");
        }
    }

    public static void consultarDetalles() {
        System.out.println("\n=== LISTA DE DETALLES ===");

        String sql = "SELECT * FROM Detalle";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int numOrden = rs.getInt("numOrden");
                int idProduct = rs.getInt("idProduct");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precioCantidad");

                System.out.printf("Orden: %d | Producto: %d | Cantidad: %d | Precio: %.2f%n",
                        numOrden, idProduct, cantidad, precio);
            }

        } catch (SQLException e) {
            System.out.println(" Error al consultar detalles: " + e.getMessage());
        }
    }

    public static void editarDetalle() {
        try {
            System.out.println("\n=== EDITAR DETALLE ===");

            System.out.print("Número de orden: ");
            int numOrden = Integer.parseInt(sc.nextLine());

            System.out.print("ID del producto: ");
            int idProduct = Integer.parseInt(sc.nextLine());

            // Verificamos si existe
            String checkSql = "SELECT * FROM Detalle WHERE numOrden = ? AND idProduct = ?";
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

                checkPs.setInt(1, numOrden);
                checkPs.setInt(2, idProduct);
                ResultSet rs = checkPs.executeQuery();

                if (!rs.next()) {
                    System.out.println(" No se encontró un detalle con esa orden y producto.");
                    return;
                }

                System.out.print("Nueva cantidad: ");
                int nuevaCantidad = Integer.parseInt(sc.nextLine());

                System.out.print("Nuevo precio por cantidad: ");
                double nuevoPrecio = Double.parseDouble(sc.nextLine());

                String updateSql = "UPDATE Detalle SET cantidad = ?, precioCantidad = ? WHERE numOrden = ? AND idProduct = ?";
                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setInt(1, nuevaCantidad);
                    updatePs.setDouble(2, nuevoPrecio);
                    updatePs.setInt(3, numOrden);
                    updatePs.setInt(4, idProduct);

                    int filas = updatePs.executeUpdate();
                    if (filas > 0) {
                        System.out.println(" Detalle actualizado correctamente.");
                    } else {
                        System.out.println(" No se pudo actualizar el detalle.");
                    }
                }
            }

        } catch (SQLException | NumberFormatException e) {
            System.out.println(" Error al editar detalle: " + e.getMessage());
        }
    }

    public static void eliminarDetalle() {
        try {
            System.out.println("\n=== ELIMINAR DETALLE ===");

            System.out.print("Número de orden: ");
            int numOrden = Integer.parseInt(sc.nextLine());

            System.out.print("ID del producto: ");
            int idProduct = Integer.parseInt(sc.nextLine());

            String sql = "DELETE FROM Detalle WHERE numOrden = ? AND idProduct = ?";

            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, numOrden);
                ps.setInt(2, idProduct);

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    System.out.println(" Detalle eliminado correctamente.");
                } else {
                    System.out.println(" No se encontró un detalle con esos datos.");
                }

            }

        } catch (SQLException | NumberFormatException e) {
            System.out.println(" Error al eliminar detalle: " + e.getMessage());
        }
    }
}
