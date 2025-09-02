package com.example.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DetalleService {
    private static Scanner sc = new Scanner(System.in);

    public static void registrarDetalle() {
        System.out.println("\n=== REGISTRAR DETALLE ===");
        
        System.out.print("Número de orden: ");
        int numOrden = Integer.parseInt(sc.nextLine());
        
        System.out.print("ID del producto: ");
        int idProduct = Integer.parseInt(sc.nextLine());
        
        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(sc.nextLine());
        
        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_insertar_detalle(?, ?, ?) }");) {
            cs.setInt(1, numOrden);
            cs.setInt(2, idProduct);
            cs.setInt(3, cantidad);
            cs.execute();
            System.out.println("Detalle registrado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar detalle: " + e.getMessage());
        }
    }

    public static void actualizarDetalle() {
        System.out.println("\n=== ACTUALIZAR DETALLE ===");
        
        System.out.print("Número de orden: ");
        int numOrden = Integer.parseInt(sc.nextLine());
        
        System.out.print("ID del producto: ");
        int idProduct = Integer.parseInt(sc.nextLine());
        
        System.out.print("Nueva cantidad: ");
        int nuevaCantidad = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_actualizar_detalle(?, ?, ?) }");) {
            cs.setInt(1, numOrden);
            cs.setInt(2, idProduct);
            cs.setInt(3, nuevaCantidad);
            cs.execute();
            System.out.println("Detalle actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar detalle: " + e.getMessage());
        }
    }

    public static void eliminarDetalle() {
        System.out.println("\n=== ELIMINAR DETALLE ===");
        
        System.out.print("Número de orden: ");
        int numOrden = Integer.parseInt(sc.nextLine());
        
        System.out.print("ID del producto: ");
        int idProduct = Integer.parseInt(sc.nextLine());
        
        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_eliminar_detalle(?, ?) }");) {
            cs.setInt(1, numOrden);
            cs.setInt(2, idProduct);
            cs.execute();
            System.out.println("Detalle eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle: " + e.getMessage());
        }
    }

    public static void consultarDetallesPorPedido() {
        System.out.println("\n=== CONSULTAR DETALLES POR PEDIDO ===");
        
        System.out.print("Número de orden: ");
        int numOrden = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_consultar_detalles_por_pedido(?) }");) {
            cs.setInt(1, numOrden);
            boolean hasResultSet = cs.execute();
            if (hasResultSet) {
                try (ResultSet rs = cs.getResultSet()) {
                    System.out.printf("%-8s %-10s %-30s %-10s %-12s\n", "Orden", "Producto", "Nombre", "Cantidad", "Precio Total");
                    System.out.println("---------------------------------------------------------------------");
                    while (rs.next()) {
                        System.out.printf("%-8d %-10d %-30s %-10d $%-12.2f\n",
                                rs.getInt("numOrden"),
                                rs.getInt("idProduct"),
                                rs.getString("nombreProduct"),
                                rs.getInt("cantidad"),
                                rs.getDouble("precioCantidad"));
                    }
                }
            } else {
                System.out.println("No hay detalles para el pedido indicado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar detalles: " + e.getMessage());
        }
    }
}
