package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.Entidades.Producto;

import com.example.enums.EstadoProducto;
import com.example.enums.TipoProducto;

public class ProductoService {

    private static Scanner sc = new Scanner(System.in);
    public static List<Producto> productos = new ArrayList<>();

    // MENÚ Gestión de Productos
    public static void gestionProductos() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Insertar nuevo producto");
            System.out.println("2. Actualizar información de producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Consultar productos");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    actualizarProducto();
                    break;
                case 3:
                    eliminarProducto();
                    break;
                case 4:
                    consultarProductos();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void registrarProducto() {

        System.out.println("\n=== REGISTRO DE PRODUCTO ===");

        System.out.println("Codigo producto: ");
        String codigo = sc.nextLine();

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

        System.out.println("Elija un estado");
        for (EstadoProducto estProducto : EstadoProducto.values()) {
            System.out.println("- " + estProducto);
        }
        String entradaEstado = sc.nextLine();

        System.out.println("Elija un tipo: ");

        for (TipoProducto tipoProducto : TipoProducto.values()) {
            System.out.println("- " + tipoProducto);
        }
        String entradaTipo = sc.nextLine();

        String sql = "INSERT INTO Producto (idProduct, nombreProduct, descripcion, cantidadDisp, stockMin, precio, estadoProducto, tipoProducto, idCat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setInt(4, cantidadDisponible);
            ps.setInt(5, stockMinimo);
            ps.setDouble(6, precio);
            ps.setString(7, entradaEstado);
            ps.setString(8, entradaTipo);
            ps.setInt(9, idCat);

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
        String codigo = sc.nextLine();

        System.out.print("Nuevo precio: ");
        double nuevoPrecio = Double.parseDouble(sc.nextLine());

        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = sc.nextLine();

        String sql = "UPDATE Producto SET precio = ?, descripcion = ? WHERE idProduct = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nuevoPrecio);
            ps.setString(2, nuevaDescripcion);
            ps.setString(3, codigo);

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
        String codigo = sc.nextLine();

        String sql = "DELETE FROM Producto WHERE idProduct = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);

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

    public static Producto buscarProductoPorCodigo(String codigo) {
        String sql = "SELECT idProduct, nombreProduct, descripcion, precio, cantidadDisp, stockMin, estadoProducto, tipoProducto, idCat "
                + "FROM Producto WHERE idProduct= ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto producto = new Producto();
                    producto.setIdProducto(rs.getInt("idProduct"));
                    producto.setNombre(rs.getString("nombreProduct"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setCantidadDisponible(rs.getInt("cantidadDisp"));
                    producto.setStockMinimo(rs.getInt("stockMin"));
                    producto.setEstado(EstadoProducto.valueOf(rs.getString("estadoProducto")));
                    producto.setTipoProducto(TipoProducto.valueOf(rs.getString("tipoProducto")));
                    producto.setCategoria(rs.getInt("idCat"));
                    return producto;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }

        return null; // No encontrado
    }

}