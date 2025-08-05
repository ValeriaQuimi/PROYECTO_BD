package com.example.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.Entidades.Cliente;
import com.example.enums.CategoriaCliente;

public class ClienteService {

    private static Scanner sc = new Scanner(System.in);

    public static void registrarCliente() {
        System.out.println("\n=== REGISTRO DE CLIENTE ===");
        
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        
        System.out.print("Teléfono: ");
        int telefono = Integer.parseInt(sc.nextLine());
        
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        
        System.out.println("Tipo (vip/estandar): ");
        String tipo = sc.nextLine();
        
        System.out.print("Ciudad: ");
        String ciudad = sc.nextLine();
        
        System.out.print("Calle: ");
        String calle = sc.nextLine();
        
        System.out.print("Referencia de dirección: ");
        String referencia = sc.nextLine();

        String sql = "INSERT INTO Cliente (nombre, telefono, correo, tipo, dir_ciudad, dir_calle, dir_referencia) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            ps.setInt(2, telefono);
            ps.setString(3, correo);
            ps.setString(4, tipo);
            ps.setString(5, ciudad);
            ps.setString(6, calle);
            ps.setString(7, referencia);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Cliente registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el cliente.");
            }
        } catch (Exception e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
    }

    public static void actualizarCliente() {
        System.out.print("\nIngrese el ID del cliente a actualizar: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo teléfono: ");
        int nuevoTelefono = Integer.parseInt(sc.nextLine());
        
        System.out.print("Nueva ciudad: ");
        String nuevaCiudad = sc.nextLine();
        
        System.out.print("Nueva calle: ");
        String nuevaCalle = sc.nextLine();
        
        System.out.print("Nueva referencia: ");
        String nuevaReferencia = sc.nextLine();

        String sql = "UPDATE Cliente SET telefono = ?, dir_ciudad = ?, dir_calle = ?, dir_referencia = ? WHERE idCliente = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuevoTelefono);
            ps.setString(2, nuevaCiudad);
            ps.setString(3, nuevaCalle);
            ps.setString(4, nuevaReferencia);
            ps.setInt(5, id);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Cliente actualizado correctamente.");
            } else {
                System.out.println("Cliente no encontrado o no se pudo actualizar.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    public static void eliminarCliente() {
        System.out.print("\nIngrese el ID del cliente a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM Cliente WHERE idCliente = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Cliente eliminado correctamente.");
            } else {
                System.out.println("Cliente no encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }

    public static void consultarClientes() {
        System.out.println("\n--- Listado de Clientes ---");

        String sql = "SELECT idCliente, nombre, telefono, correo, tipo, dir_ciudad, dir_calle, dir_referencia FROM Cliente";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                    "ID: %d | Nombre: %s | Teléfono: %d | Correo: %s | Tipo: %s | Dirección: %s, %s, %s\n",
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getInt("telefono"),
                    rs.getString("correo"),
                    rs.getString("tipo"),
                    rs.getString("dir_ciudad"),
                    rs.getString("dir_calle"),
                    rs.getString("dir_referencia")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar clientes: " + e.getMessage());
        }
    }

    public static Cliente buscarClientePorId(int id) {
        String sql = "SELECT idCliente, nombre, telefono, correo, tipo, dir_ciudad, dir_calle, dir_referencia FROM Cliente WHERE idCliente = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("idCliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setTelefono(rs.getInt("telefono"));
                    cliente.setCorreo(rs.getString("correo"));
                    cliente.setCategoria(CategoriaCliente.valueOf(rs.getString("tipo").toUpperCase()));
                    // Puedes agregar los campos de dirección si los necesitas
                    return cliente;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return null; // No encontrado
    }
}
