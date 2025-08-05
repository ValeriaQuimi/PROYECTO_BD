package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.Entidades.Producto;

public class ProductoService {

    private static Scanner sc = new Scanner(System.in);

    public static void registrarProducto() {

        System.out.println("\n=== REGISTRO DE PRODUCTO ===");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Precio: ");
        double precio = Double.parseDouble(sc.nextLine());

        System.out.println("Cantidad Disponible: ");
        int cantidadDisponible = Integer.parseInt(sc.nextLine());

        System.out.print("Stock Minimo: ");
        int stockMinimo = Integer.parseInt(sc.nextLine());

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("ID Categoría: ");
        int idCat = Integer.parseInt(sc.nextLine());

        System.out.println("Elija un estado (disponible, agotado):");
        String entradaEstado = sc.nextLine().toLowerCase();

        System.out.println("Elija un tipo (personalizado, estandar) : ");
        String entradaTipo = sc.nextLine().toLowerCase();

        String sql = "INSERT INTO Producto (nombreProduct, descripcion, cantidadDisp, stockMin, precio, estadoProducto, tipoProducto, idCat) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, cantidadDisponible);
            ps.setInt(4, stockMinimo);
            ps.setDouble(5, precio);
            ps.setString(6, entradaEstado.toLowerCase());
            ps.setString(7, entradaTipo.toLowerCase());
            ps.setInt(8, idCat);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Producto registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el producto.");
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error al registrar producto: " + e.getMessage());
        }
    }

    public static void actualizarProducto() {
        System.out.print("\nIngrese el código del producto a actualizar: ");
        int codigo = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo precio: ");
        double nuevoPrecio = Double.parseDouble(sc.nextLine());

        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = sc.nextLine();

        String sql = "UPDATE Producto SET precio = ?, descripcion = ? WHERE idProduct = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nuevoPrecio);
            ps.setString(2, nuevaDescripcion);
            ps.setInt(3, codigo);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("Producto no encontrado o no se pudo actualizar.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    // Eliminar Producto
    public static void eliminarProducto() {
        System.out.print("\nIngrese el código del producto a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Producto WHERE idProduct = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("Producto no encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    // Consultar Productos
    public static void consultarProductos() {
        System.out.println("\n--- Listado de Productos ---");

        String sql = "SELECT p.idProduct, p.nombreProduct, p.descripcion, p.precio, p.cantidadDisp, p.stockMin, p.estadoProducto, p.tipoProducto, c.nombreCat "
                + "FROM Producto p JOIN Categoria c ON p.idCat = c.idCat";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "ID: %d | Nombre: %s | Descripción: %s | Precio: %.2f | Cantidad: %d | Stock Mín: %d | Estado: %s | Tipo: %s | Categoría: %s\n",
                        rs.getInt("idProduct"),
                        rs.getString("nombreProduct"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidadDisp"),
                        rs.getInt("stockMin"),
                        rs.getString("estadoProducto"),
                        rs.getString("tipoProducto"),
                        rs.getString("nombreCat"));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar productos: " + e.getMessage());
        }
    }

    // Buscar Producto por Código

    public static Producto buscarProductoPorCodigo(int codigo) {
        String sql = "SELECT idProduct, nombreProduct, descripcion, precio, cantidadDisp, stockMin, estadoProducto, tipoProducto, idCat "
                + "FROM Producto WHERE idProduct= ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
         
                    int id= rs.getInt("idProduct");
                    String nombre= rs.getString("nombreProduct");
                    String descripcion= rs.getString("descripcion");
                    double precio= rs.getDouble("precio");
                    int cantidadDisp= rs.getInt("cantidadDisp");
                    int stockMin= rs.getInt("stockMin");
                    String estado= rs.getString("estadoProducto");
                    String tipo= rs.getString("tipoProducto");
                    int categoria= rs.getInt("idCat");
                  
                     System.out.printf(
                    "ID: %d\nNombre: %s\nDescripción: %s\nPrecio: %.2f\nCantidad Disponible: %d\nStock Mínimo: %d\nEstado: %s\nTipo: %s\nCategoría: %d\n",
                    id, nombre, descripcion, precio, cantidadDisp, stockMin, estado, tipo, categoria);
            } 
             else {
                System.out.println("Producto no encontrado");
            }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }

        return null; // No encontrado
    }

}