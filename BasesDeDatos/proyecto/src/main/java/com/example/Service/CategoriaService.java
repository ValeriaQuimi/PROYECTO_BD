package com.example.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CategoriaService {
    private static Scanner sc = new Scanner(System.in);

    public static void registrarCategoria() {
        try {
            System.out.println("\n=== REGISTRO DE CATEGORÍA ===");

            System.out.print("Nombre de la categoría: ");
            String nombre = sc.nextLine();

            System.out.print("Descripción: ");
            String descripcion = sc.nextLine();

            try (Connection conn = ConexionBD.getConnection();
                 CallableStatement cs = conn.prepareCall("{ CALL sp_insertarCategoria(?, ?) }")) {

                cs.setString(1, nombre);
                cs.setString(2, descripcion);

                cs.execute();
                System.out.println("Categoría registrada correctamente.");

            } catch (SQLException e) {
                System.out.println("Error del SP al registrar categoría: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error general al registrar categoría: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error en la entrada de datos.");
        }
    }

    public static void eliminarCategoria() {
        System.out.print("\nIngrese el ID de la categoría a eliminar: ");
        int idCat = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_eliminarCategoria(?) }")) {

            cs.setInt(1, idCat);
            cs.execute();
            System.out.println("Categoría eliminada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error del SP al eliminar categoría: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al eliminar categoría: " + e.getMessage());
        }
    }

    public static void consultarCategorias() {
        System.out.println("\n=== LISTA DE CATEGORÍAS ===");

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT idCat, nombreCat, descripcion FROM Categoria")) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idCat");
                    String nombre = rs.getString("nombreCat");
                    String descripcion = rs.getString("descripcion");

                    System.out.printf("ID: %d | Nombre: %s | Descripción: %s\n", id, nombre, descripcion);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar categorías: " + e.getMessage());
        }
    }

    public static void editarCategoria() {
        System.out.print("\nIngrese el ID de la categoría a editar: ");
        int idCat = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo nombre: ");
        String nuevoNombre = sc.nextLine();

        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = sc.nextLine();

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_actualizarCategoria(?, ?, ?) }")) {

            cs.setInt(1, idCat);
            cs.setString(2, nuevoNombre);
            cs.setString(3, nuevaDescripcion);

            cs.execute();
            System.out.println("Categoría actualizada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error del SP al actualizar categoría: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al actualizar categoría: " + e.getMessage());
        }
    }

}

