package com.example.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class ProductoService {

    private static Scanner sc = new Scanner(System.in);

    public static void registrarProducto() {

        System.out.println("\n=== REGISTRO DE PRODUCTO ===");

        try (Connection conn = ConexionBD.getConnection()) {
            System.out.print("nombre: "); String nombre = sc.nextLine();
            System.out.print("descripcion: ");   String descripcion = sc.nextLine();
            System.out.print("cantidad Disponible: ");  int cantidad = Integer.parseInt(sc.nextLine());
            System.out.print("stock Minimo: ");      int stockMin = Integer.parseInt(sc.nextLine());
            System.out.print("precio: ");        double precio = Double.parseDouble(sc.nextLine());
            System.out.print("estado Producto: ");String estado = sc.nextLine().toLowerCase();
            System.out.print("tipo Producto: ");  String tipo = sc.nextLine().toLowerCase();
            System.out.print("idCat: ");         int idCat = Integer.parseInt(sc.nextLine());

            try (CallableStatement cs = conn.prepareCall("{ CALL sp_insertarProducto(?,?,?,?,?,?,?,?) }")) {
                cs.setString(1, nombre);
                cs.setString(2, descripcion);
                cs.setInt(3, cantidad);
                cs.setInt(4, stockMin);
                cs.setDouble(5, precio);
                cs.setString(6, estado);
                cs.setString(7, tipo);
                cs.setInt(8, idCat);
                cs.execute();
                System.out.println("Producto registrado correctamente usando SP.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar producto: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al registrar producto: " + e.getMessage());
        }
}

public static void actualizarProducto() {
    System.out.print("\nIngrese el código del producto a actualizar: ");
    int id = Integer.parseInt(sc.nextLine());

    System.out.print("Nuevo precio: ");
    double nuevoPrecio = Double.parseDouble(sc.nextLine());

    System.out.print("Nueva descripción: ");
    String nuevaDescripcion = sc.nextLine();

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall("{ CALL sp_actualizarProducto(?, ?, ?) }")) {
        
        cs.setInt(1, id);
        cs.setDouble(2, nuevoPrecio);
        cs.setString(3, nuevaDescripcion);

        cs.execute();
        System.out.println("Producto actualizado correctamente usando SP.");

    } catch (SQLException e) {
        System.out.println("Error al actualizar producto: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Error general al actualizar producto: " + e.getMessage());
    }
}


    // Eliminar Producto
    public static void eliminarProducto() {
        System.out.print("\nIngrese el código del producto a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_eliminarProducto(?) }")) {

            cs.setInt(1, id);
            cs.execute();
            System.out.println("Producto eliminado correctamente usando SP.");

        } catch (SQLException e) {
            System.out.println("Error  al eliminar producto: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al eliminar producto: " + e.getMessage());
        }
    }

    // Consultar Productos
    public static void consultarProductos() {
        System.out.println("\n--- Listado de Productos ---");

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_consultarProductos() }");
             ResultSet rs = cs.executeQuery()) {

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
        } catch (Exception e) {
            System.out.println("Error general al consultar productos: " + e.getMessage());
        }
    }

    // Buscar Producto por Código

    public static void buscarProductoPorCodigo(int codigo) {
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

    }

}
