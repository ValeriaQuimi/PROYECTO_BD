package com.example.Service;

import java.sql.*;
import java.util.*;

public class DetalleService {

    private static Scanner sc = new Scanner(System.in);

    public static void registrarDetalle() {
        try (Connection conn = ConexionBD.getConnection()) {

            int numOrden = seleccionarPedido(conn);
            if (numOrden == -1) return;

            int idProduct = seleccionarProducto(conn);
            if (idProduct == -1) return;

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(sc.nextLine());

            System.out.print("Precio por cantidad: ");
            double precioCantidad = Double.parseDouble(sc.nextLine());

            String sql = "INSERT INTO detalle (numOrden, idProduct, cantidad, precioCantidad) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, numOrden);
                ps.setInt(2, idProduct);
                ps.setInt(3, cantidad);
                ps.setDouble(4, precioCantidad);

                int filas = ps.executeUpdate();
                System.out.println(filas > 0 ? "Detalle registrado correctamente." : "No se pudo registrar el detalle.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void eliminarDetalle() {
        try (Connection conn = ConexionBD.getConnection()) {

            int numOrden = seleccionarPedido(conn);
            if (numOrden == -1) return;

            int idProduct = seleccionarProducto(conn);
            if (idProduct == -1) return;

            String sql = "DELETE FROM detalle WHERE numOrden = ? AND idProduct = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, numOrden);
                ps.setInt(2, idProduct);

                int filas = ps.executeUpdate();
                System.out.println(filas > 0 ? "Detalle eliminado correctamente." : "No se encontró el detalle.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void consultarDetalles() {
        String sql = "SELECT * FROM detalle";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== LISTA DE DETALLES ===");

            while (rs.next()) {
                int numOrden = rs.getInt("numOrden");
                int idProduct = rs.getInt("idProduct");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precioCantidad");

                System.out.printf("Pedido #%d | Producto ID: %d | Cantidad: %d | Precio: %.2f\n",
                        numOrden, idProduct, cantidad, precio);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar detalles: " + e.getMessage());
        }
    }

    public static void editarDetalle() {
        try (Connection conn = ConexionBD.getConnection()) {

            int numOrden = seleccionarPedido(conn);
            if (numOrden == -1) return;

            int idProduct = seleccionarProducto(conn);
            if (idProduct == -1) return;

            // Verificar si existe el detalle
            String sqlCheck = "SELECT * FROM detalle WHERE numOrden = ? AND idProduct = ?";
            try (PreparedStatement check = conn.prepareStatement(sqlCheck)) {
                check.setInt(1, numOrden);
                check.setInt(2, idProduct);
                ResultSet rs = check.executeQuery();
                if (!rs.next()) {
                    System.out.println("Detalle no encontrado.");
                    return;
                }
            }

            System.out.print("Nueva cantidad: ");
            int nuevaCantidad = Integer.parseInt(sc.nextLine());

            System.out.print("Nuevo precio por cantidad: ");
            double nuevoPrecio = Double.parseDouble(sc.nextLine());

            String updateSql = "UPDATE detalle SET cantidad = ?, precioCantidad = ? WHERE numOrden = ? AND idProduct = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, nuevaCantidad);
                ps.setDouble(2, nuevoPrecio);
                ps.setInt(3, numOrden);
                ps.setInt(4, idProduct);

                int filas = ps.executeUpdate();
                System.out.println(filas > 0 ? "Detalle actualizado correctamente." : "No se pudo actualizar.");
            }

        } catch (Exception e) {
            System.out.println("Error al editar detalle: " + e.getMessage());
        }
    }

    // -------------------------------------------
    // MÉTODOS AUXILIARES PARA MOSTRAR OPCIONES
    // -------------------------------------------

    private static int seleccionarPedido(Connection conn) throws SQLException {
        String sql = "SELECT numOrden, idCliente FROM pedido";
        List<Integer> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int i = 1;
            while (rs.next()) {
                int id = rs.getInt("numOrden");
                int cliente = rs.getInt("idCliente");
                System.out.printf("%d. Pedido #%d (Cliente ID: %d)\n", i, id, cliente);
                lista.add(id);
                i++;
            }
        }

        if (lista.isEmpty()) {
            System.out.println("No hay pedidos disponibles.");
            return -1;
        }

        System.out.print("Seleccione un pedido: ");
        int opcion = Integer.parseInt(sc.nextLine());

        if (opcion < 1 || opcion > lista.size()) {
            System.out.println("Opción inválida.");
            return -1;
        }

        return lista.get(opcion - 1);
    }

    private static int seleccionarProducto(Connection conn) throws SQLException {
        String sql = "SELECT idProduct, nombreProduct FROM producto";
        List<Integer> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int i = 1;
            while (rs.next()) {
                int id = rs.getInt("idProduct");
                String nombre = rs.getString("nombreProduct");
                System.out.printf("%d. %s (ID: %d)\n", i, nombre, id);
                lista.add(id);
                i++;
            }
        }

        if (lista.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return -1;
        }

        System.out.print("Seleccione un producto: ");
        int opcion = Integer.parseInt(sc.nextLine());

        if (opcion < 1 || opcion > lista.size()) {
            System.out.println("Opción inválida.");
            return -1;
        }

        return lista.get(opcion - 1);
    }
}
