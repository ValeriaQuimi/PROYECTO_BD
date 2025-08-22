package com.example.Service;

import java.sql.*;
import java.util.Scanner;

public class CategoriaService {

    private static Scanner sc = new Scanner(System.in);

    public static void registrarCategoria() {
        System.out.println("\n=== REGISTRO DE CATEGORÍA ===");

        System.out.print("Nombre de la categoría: ");
        String nombre = sc.nextLine();

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        String sql = "{ CALL sp_insertarCategoria(?, ?) }";

        try (Connection conn = ConexionBD.getConnection();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombre);
            cs.setString(2, descripcion);

            cs.execute();
            System.out.println("Categoría registrada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al registrar categoría: " + e.getMessage());
        }
    }

    public static void editarCategoria() {
        System.out.println("\n=== EDICIÓN DE CATEGORÍA ===");

        System.out.print("Ingrese el ID de la categoría a editar: ");
        int idCat = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo nombre: ");
        String nuevoNombre = sc.nextLine();

        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = sc.nextLine();

        String sql = "{ CALL sp_actualizarCategoria(?, ?, ?) }";

        try (Connection conn = ConexionBD.getConnection();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCat);
            cs.setString(2, nuevoNombre);
            cs.setString(3, nuevaDescripcion);

            cs.execute();
            System.out.println("Categoría actualizada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar categoría: " + e.getMessage());
        }
    }

    public static void eliminarCategoria() {
        System.out.println("\n=== ELIMINACIÓN DE CATEGORÍA ===");

        System.out.print("Ingrese el ID de la categoría a eliminar: ");
        int idCat = Integer.parseInt(sc.nextLine());

        String sql = "{ CALL sp_eliminarCategoria(?) }";

        try (Connection conn = ConexionBD.getConnection();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCat);

            cs.execute();
            System.out.println("Categoría eliminada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
        }
    }

    public static void consultarCategorias() {
        System.out.println("\n=== LISTA DE CATEGORÍAS ===");

        String sql = "SELECT idCat, nombreCat, descripcion FROM Categoria";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idCat");
                String nombre = rs.getString("nombreCat");
                String descripcion = rs.getString("descripcion");

                System.out.printf("ID: %d | Nombre: %s | Descripción: %s%n", id, nombre, descripcion);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar categorías: " + e.getMessage());
        }
    }
}
