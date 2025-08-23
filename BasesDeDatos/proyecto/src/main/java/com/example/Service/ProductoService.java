package com.example.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


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

        
        String sql = "{CALL sp_insertarProducto(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombre);
            cs.setString(2, descripcion);
            cs.setInt(3, cantidadDisponible);
            cs.setInt(4, stockMinimo);
            cs.setDouble(5, precio);
            cs.setString(6, entradaEstado);
            cs.setString(7, entradaTipo);
            cs.setInt(8, idCat);

            cs.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void actualizarProducto() {
        System.out.print("\nIngrese el código del producto a actualizar: ");
        int codigo = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo precio: ");
        double nuevoPrecio = Double.parseDouble(sc.nextLine());

        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = sc.nextLine();

        
        String sql = "{CALL sp_actualizarProducto(?, ?, ?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, codigo);
            cs.setDouble(2, nuevoPrecio);
            cs.setString(3, nuevaDescripcion);

            cs.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Eliminar Producto
    public static void eliminarProducto() {
        System.out.print("\nIngrese el código del producto a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        
        String sql = "{CALL sp_eliminarProducto(?)}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Consultar Productos
    public static void consultarProductos() {
        System.out.println("\n--- Listado de Productos ---");

        
        String sql = "{CALL sp_consultarProductos()}";

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
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
            System.out.println(e.getMessage());
        }
    }

}