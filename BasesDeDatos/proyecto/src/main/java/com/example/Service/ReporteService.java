package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReporteService {
    private static Scanner sc = new Scanner(System.in);

    public static void mostrarReporteCategoriaMasVendida() {
        System.out.println("\n=== REPORTE: CATEGORÍA MÁS VENDIDA ===");
        
        String sql = "SELECT * FROM reporteCategoriaMasVendida";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            System.out.printf("%-20s %-25s %-20s\n", "Categoría", "Total Cantidad", "Número Clientes");
            System.out.println("------------------------------------------------------------");
            
            while (rs.next()) {
                String categoria = rs.getString("Categoria");
                int totalCantidad = rs.getInt("total_cantidad_pedida");
                int numeroClientes = rs.getInt("numero_clientes");
                
                System.out.printf("%-20s %-25d %-20d\n", categoria, totalCantidad, numeroClientes);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al generar reporte de categorías más vendidas: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al generar reporte: " + e.getMessage());
        }
    }

    public static void mostrarReporteHistorialCompras() {
        System.out.println("\n=== REPORTE: HISTORIAL DE COMPRAS DE CLIENTES ===");
        
        String sql = "SELECT * FROM reporteHistorialDeComprasDeClientes ORDER BY idCliente, numOrden";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            System.out.printf("%-8s %-20s %-30s %-10s %-8s %-12s %-8s %-25s %-15s %-12s\n", 
                             "Cliente", "Nombre", "Correo", "Tipo", "Pedido", "Precio Total", "Cantidad", "Producto", "Tipo Producto", "Precio Cantidad");
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombreCliente = rs.getString("nombreCliente");
                String correo = rs.getString("correo");
                String tipo = rs.getString("tipo");
                int numOrden = rs.getInt("numOrden");
                double precioTotal = rs.getDouble("precioTotal");
                int cantidad = rs.getInt("cantidad");
                String nombreProducto = rs.getString("nombreProduct");
                String tipoProducto = rs.getString("tipoProducto");
                double precioCantidad = rs.getDouble("precioCantidad");
                
                System.out.printf("%-8d %-20s %-30s %-10s %-8d %-12.2f %-8d %-25s %-15s %-12.2f\n",
                                idCliente, nombreCliente, correo, tipo, numOrden, precioTotal, cantidad, 
                                nombreProducto, tipoProducto, precioCantidad);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al generar reporte de historial de compras: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al generar reporte: " + e.getMessage());
        }
    }

    public static void mostrarReportePagosDelMes() {
        System.out.println("\n=== REPORTE: PAGOS DEL MES ACTUAL ===");
        
        String sql = "SELECT * FROM reportePagosDelMes";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            System.out.printf("%-8s %-12s %-12s %-15s %-8s %-20s %-30s %-15s\n", 
                             "ID Pago", "Monto", "Fecha", "Método", "Pedido", "Cliente", "Correo", "Tipo Cliente");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            
            while (rs.next()) {
                int idPago = rs.getInt("ID");
                double monto = rs.getDouble("Monto");
                String fecha = rs.getString("Fecha");
                String metodo = rs.getString("Metodo");
                int pedido = rs.getInt("Pedido");
                String cliente = rs.getString("Cliente");
                String correo = rs.getString("Correo");
                String tipoCliente = rs.getString("TipoCliente");
                
                System.out.printf("%-8d $%-12.2f %-12s %-15s %-8d %-20s %-30s %-15s\n",
                                idPago, monto, fecha, metodo, pedido, cliente, correo, tipoCliente);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al generar reporte de pagos del mes: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al generar reporte: " + e.getMessage());
        }
    }

    public static void mostrarReporteEntregasRepartidorCliente() {
        System.out.println("\n=== REPORTE: ENTREGAS POR REPARTIDOR Y CLIENTE ===");
        
        String sql = "SELECT * FROM vista_entregas_repartidor_cliente ORDER BY numEntrega";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            System.out.printf("%-8s %-12s %-15s %-20s %-20s %-15s\n", 
                             "Entrega", "Fecha", "Estado", "Repartidor", "Cliente", "Teléfono");
            System.out.println("----------------------------------------------------------------------------");
            
            while (rs.next()) {
                int numEntrega = rs.getInt("numEntrega");
                String fechaEntrega = rs.getString("fechaEntrega");
                String estadoEntrega = rs.getString("estadoEntrega");
                String nombreRepartidor = rs.getString("nombreRepartidor");
                String nombreCliente = rs.getString("nombreCliente");
                String telefono = rs.getString("telefono");
                
                System.out.printf("%-8d %-12s %-15s %-20s %-20s %-15s\n",
                                numEntrega, fechaEntrega, estadoEntrega, nombreRepartidor, nombreCliente, telefono);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al generar reporte de entregas: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al generar reporte: " + e.getMessage());
        }
    }

    public static void mostrarReporteProductosStockBajo() {
        System.out.println("\n=== REPORTE: PRODUCTOS CON STOCK BAJO ===");
        
        String sql = "SELECT p.idProduct, p.nombreProduct, p.cantidadDisp, p.stockMin, " +
                     "c.nombreCat, p.estadoProducto " +
                     "FROM Producto p " +
                     "JOIN Categoria c ON p.idCat = c.idCat " +
                     "WHERE p.cantidadDisp <= p.stockMin " +
                     "ORDER BY p.cantidadDisp ASC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            System.out.printf("%-8s %-25s %-15s %-12s %-20s %-15s\n", 
                             "ID", "Producto", "Stock Actual", "Stock Mín", "Categoría", "Estado");
            System.out.println("----------------------------------------------------------------------------");
            
            while (rs.next()) {
                int idProduct = rs.getInt("idProduct");
                String nombreProduct = rs.getString("nombreProduct");
                int cantidadDisp = rs.getInt("cantidadDisp");
                int stockMin = rs.getInt("stockMin");
                String nombreCat = rs.getString("nombreCat");
                String estadoProducto = rs.getString("estadoProducto");
                
                System.out.printf("%-8d %-25s %-15d %-12d %-20s %-15s\n",
                                idProduct, nombreProduct, cantidadDisp, stockMin, nombreCat, estadoProducto);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al generar reporte de productos con stock bajo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al generar reporte: " + e.getMessage());
        }
    }

    public static void mostrarReporteClientesVIP() {
        System.out.println("\n=== REPORTE: CLIENTES VIP ===");
        
        String sql = "SELECT c.idCliente, c.nombre, c.correo, c.telefono, " +
                     "COUNT(p.numOrden) as totalPedidos, " +
                     "SUM(p.precioTotal) as totalGastado " +
                     "FROM Cliente c " +
                     "LEFT JOIN Pedido p ON c.idCliente = p.idCliente " +
                     "WHERE c.tipo = 'vip' " +
                     "GROUP BY c.idCliente, c.nombre, c.correo, c.telefono " +
                     "ORDER BY totalGastado DESC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            System.out.printf("%-8s %-20s %-30s %-15s %-15s %-15s\n", 
                             "ID", "Nombre", "Correo", "Teléfono", "Total Pedidos", "Total Gastado");
            System.out.println("----------------------------------------------------------------------------");
            
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                int totalPedidos = rs.getInt("totalPedidos");
                double totalGastado = rs.getDouble("totalGastado");
                
                System.out.printf("%-8d %-20s %-30s %-15s %-15d $%-15.2f\n",
                                idCliente, nombre, correo, telefono, totalPedidos, totalGastado);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al generar reporte de clientes VIP: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al generar reporte: " + e.getMessage());
        }
    }
}
