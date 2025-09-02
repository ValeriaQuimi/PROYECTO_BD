package com.example.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PedidoService {

    private static Scanner sc = new Scanner(System.in);

    public static void crearPedido() {
        System.out.println("\n=== CREAR PEDIDO ===");

        System.out.print("Precio total: ");
        double precioTotal = Double.parseDouble(sc.nextLine());
        
        System.out.print("Saldo restante: ");
        double saldoRestante = Double.parseDouble(sc.nextLine());
        
        System.out.print("Tipo de pedido (domicilio/local): ");
        String tipoPedido = sc.nextLine();
        
        System.out.print("Estado del pedido (preparando/entregada/anulado): ");
        String estadoPedido = sc.nextLine();
        
        System.out.print("ID del cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_insertarPedido(?, ?, ?, ?, ?) }")) {
            
            cs.setDouble(1, precioTotal);
            cs.setDouble(2, saldoRestante);
            cs.setString(3, tipoPedido);
            cs.setString(4, estadoPedido);
            cs.setInt(5, idCliente);
            
            cs.execute();
            System.out.println("Pedido creado correctamente usando SP.");
            
        } catch (SQLException e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al crear pedido: " + e.getMessage());
        }
    }

    public static void actualizarPedido() {
        System.out.print("\nIngrese el número de orden del pedido a actualizar: ");
        int numOrden = Integer.parseInt(sc.nextLine());
        
        System.out.print("Nuevo precio total: ");
        double nuevoPrecioTotal = Double.parseDouble(sc.nextLine());
        
        System.out.print("Nuevo saldo restante: ");
        double nuevoSaldoRestante = Double.parseDouble(sc.nextLine());
        
        System.out.print("Nuevo tipo de pedido (domicilio/local): ");
        String nuevoTipoPedido = sc.nextLine();
        
        System.out.print("Nuevo estado del pedido (preparando/entregada/anulado): ");
        String nuevoEstadoPedido = sc.nextLine();

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_actualizarPedido(?, ?, ?, ?, ?) }")) {
            
            cs.setInt(1, numOrden);
            cs.setDouble(2, nuevoPrecioTotal);
            cs.setDouble(3, nuevoSaldoRestante);
            cs.setString(4, nuevoTipoPedido);
            cs.setString(5, nuevoEstadoPedido);
            
            cs.execute();
            System.out.println("Pedido actualizado correctamente usando SP.");
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al actualizar pedido: " + e.getMessage());
        }
    }

    public static void eliminarPedido() {
        System.out.print("\nIngrese el número de orden del pedido a eliminar: ");
        int numOrden = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_eliminarPedido(?) }")) {
            
            cs.setInt(1, numOrden);
            cs.execute();
            System.out.println("Pedido eliminado correctamente usando SP.");
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al eliminar pedido: " + e.getMessage());
        }
    }

    public static void listarPedidos() {
        System.out.println("\n=== LISTADO DE PEDIDOS ===");
        
        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_consultarPedidos() }");
             ResultSet rs = cs.executeQuery()) {
            
            System.out.printf("%-8s %-12s %-15s %-20s %-10s\n", 
                             "Orden", "Precio Total", "Saldo Restante", "Tipo", "Estado");
            System.out.println("------------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-8d $%-12.2f $%-15.2f %-20s %-10s\n",
                                rs.getInt("numOrden"),
                                rs.getDouble("precioTotal"),
                                rs.getDouble("saldoRestante"),
                                rs.getString("tipoPedido"),
                                rs.getString("estadoPedido"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al listar pedidos: " + e.getMessage());
        }
    }

}
