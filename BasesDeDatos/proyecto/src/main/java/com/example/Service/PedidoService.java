package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PedidoService {

    private static Scanner sc = new Scanner(System.in);

    public static void crearPedido() {
        System.out.println("\n=== CREAR PEDIDO ===");

        System.out.print("Ingrese ID del cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());

        if (!clienteExiste(idCliente)) {
            System.out.println("Error: El cliente no existe");
            return;
        }

        String sqlPedido = "INSERT INTO Pedido (fechaPedido, estadoPedido, idCliente) VALUES (CURRENT_DATE, 'pendiente', ?)";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, idCliente);
            
            if (ps.executeUpdate() > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idPedido = generatedKeys.getInt(1);
                    System.out.println("Pedido #" + idPedido + " creado");
                    agregarProductos(idPedido);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private static boolean clienteExiste(int idCliente) {
        String sql = "SELECT 1 FROM Cliente WHERE idCliente = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Error al verificar cliente: " + e.getMessage());
            return false;
        }
    }

    private static void agregarProductos(int idPedido) {
        System.out.println("\n--- Agregar productos ---");
        char continuar;
        
        do {

            System.out.print("ID del producto: ");
            int idProducto = Integer.parseInt(sc.nextLine());
            
            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(sc.nextLine());
            

            if (!verificarDisponibilidad(idProducto, cantidad)) {
                System.out.println("Producto no disponible");
                continue;
            }
            
            String sql = "INSERT INTO DetallePedido (idPedido, idProducto, cantidad, precioUnitario) " +
                         "VALUES (?, ?, ?, (SELECT precio FROM Producto WHERE idProduct = ?))";
            
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setInt(1, idPedido);
                ps.setInt(2, idProducto);
                ps.setInt(3, cantidad);
                ps.setInt(4, idProducto);
                
                if (ps.executeUpdate() > 0) {
                    actualizarStock(idProducto, -cantidad);
                    System.out.println("Producto agregado");
                }
            } catch (SQLException e) {
                System.out.println("Error al agregar producto: " + e.getMessage());
            }
            
            System.out.print("¿Agregar otro producto? (s/n): ");
            continuar = sc.nextLine().toLowerCase().charAt(0);
        } while (continuar == 's');
    }

    private static boolean verificarDisponibilidad(int idProducto, int cantidad) {
        String sql = "SELECT cantidadDisp FROM Producto WHERE idProduct = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            
            return rs.next() && rs.getInt("cantidadDisp") >= cantidad;
        } catch (SQLException e) {
            System.out.println("Error al verificar stock: " + e.getMessage());
            return false;
        }
    }

    private static void actualizarStock(int idProducto, int cantidad) {
        String sql = "UPDATE Producto SET cantidadDisp = cantidadDisp + ? WHERE idProduct = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
        }
    }

    public static void listarPedidos() {
        System.out.println("\n=== LISTADO DE PEDIDOS ===");
        
        String sql = "SELECT p.idPedido, p.fechaPedido, p.estadoPedido, c.nombreCliente, " +
                     "SUM(d.cantidad * d.precioUnitario) AS total " +
                     "FROM Pedido p " +
                     "JOIN Cliente c ON p.idCliente = c.idCliente " +
                     "LEFT JOIN DetallePedido d ON p.idPedido = d.idPedido " +
                     "GROUP BY p.idPedido, p.fechaPedido, p.estadoPedido, c.nombreCliente";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            System.out.printf("%-8s %-12s %-15s %-20s %-10s\n", 
                             "ID", "Fecha", "Estado", "Cliente", "Total");
            System.out.println("------------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-8d %-12s %-15s %-20s $%-10.2f\n",
                                rs.getInt("idPedido"),
                                rs.getDate("fechaPedido"),
                                rs.getString("estadoPedido"),
                                rs.getString("nombreCliente"),
                                rs.getDouble("total"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
    }

    public static void eliminarPedidos() {
        System.out.print("\nID del pedido a eliminar: ");
        int idPedido = Integer.parseInt(sc.nextLine());
        
    
        String sqlDetalle = "DELETE FROM DetallePedido WHERE idPedido = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlDetalle)) {
            
            ps.setInt(1, idPedido);
            ps.executeUpdate();
            

            String sqlPedido = "DELETE FROM Pedido WHERE idPedido = ?";
            try (PreparedStatement psPedido = conn.prepareStatement(sqlPedido)) {
                psPedido.setInt(1, idPedido);
                
                if (psPedido.executeUpdate() > 0) {
                    System.out.println("Pedido eliminado correctamente");
                } else {
                    System.out.println("No se encontró el pedido");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }
}
