package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CategoriaService {
    private static Scanner sc = new Scanner(System.in);

    public static void gestionCategorias() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Registrar nueva categoría");
            System.out.println("2. Eliminar categoría");
            System.out.println("3. Consultar categorías");
            System.out.println("4. Editar categoria");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarCategoria();
                    break;
                case 2:
                    eliminarCategoria();
                    break;
                case 3:
                    consultarCategorias();
                    break;
                case 4:
                    editarCategoria();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void registrarCategoria() {
        try {
            System.out.println("\n=== REGISTRO DE CATEGORÍA ===");

            System.out.print("Nombre de la categoría: ");
            String nombre = sc.nextLine();

            System.out.print("Descripción: ");
            String descripcion = sc.nextLine();

            String sql = "INSERT INTO Categoria (nombreCat, descripcion) VALUES (?, ?)";

            try (Connection conn = ConexionBD.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, nombre);
                ps.setString(2, descripcion);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println("Categoría registrada correctamente.");
                } else {
                    System.out.println("No se pudo registrar la categoría.");
                }

            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error en la entrada de datos.");
        }
    }

    public static void eliminarCategoria() {
        System.out.print("\nIngrese el ID de la categoría a eliminar: ");
        int idCat = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Categoria WHERE idCat = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCat);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Categoría eliminada correctamente.");
            } else {
                System.out.println("No se encontró una categoría con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar la categoría: " + e.getMessage());
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

                System.out.printf("ID: %d | Nombre: %s | Descripción: %s\n", id, nombre, descripcion);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar categorías: " + e.getMessage());
        }
    }

    public static void editarCategoria() {
        System.out.print("\nIngrese el ID de la categoría a editar: ");
        int idCat = Integer.parseInt(sc.nextLine());

        String verificarSql = "SELECT * FROM Categoria WHERE idCat = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement verificarPs = conn.prepareStatement(verificarSql)) {

            verificarPs.setInt(1, idCat);
            try (ResultSet rs = verificarPs.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Categoría no encontrada.");
                    return;
                }
            }

            System.out.print("Nuevo nombre: ");
            String nuevoNombre = sc.nextLine();

            System.out.print("Nueva descripción: ");
            String nuevaDescripcion = sc.nextLine();

            String updateSql = "UPDATE Categoria SET nombreCat = ?, descripcion = ? WHERE idCat = ?";
            try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                updatePs.setString(1, nuevoNombre);
                updatePs.setString(2, nuevaDescripcion);
                updatePs.setInt(3, idCat);

                int filas = updatePs.executeUpdate();
                if (filas > 0) {
                    System.out.println("Categoría actualizada correctamente.");
                } else {
                    System.out.println("No se pudo actualizar la categoría.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al editar categoría: " + e.getMessage());
        }
    }

}
