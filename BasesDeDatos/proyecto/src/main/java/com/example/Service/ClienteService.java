package com.example.Service;

import java.sql.CallableStatement;
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

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_insertarCliente(?, ?, ?, ?, ?, ?, ?) }")) {
            
            cs.setString(1, nombre);
            cs.setInt(2, telefono);
            cs.setString(3, correo);
            cs.setString(4, tipo);
            cs.setString(5, ciudad);
            cs.setString(6, calle);
            cs.setString(7, referencia);

            cs.execute();
            System.out.println("Cliente registrado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error del SP al registrar cliente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al registrar cliente: " + e.getMessage());
        }
    }

    public static void actualizarCliente() {
        System.out.print("\nIngrese el ID del cliente a actualizar: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo nombre: ");
        String nuevoNombre = sc.nextLine();
        
        System.out.print("Nuevo teléfono: ");
        int nuevoTelefono = Integer.parseInt(sc.nextLine());
        
        System.out.print("Nuevo correo: ");
        String nuevoCorreo = sc.nextLine();
        
        System.out.print("Nuevo tipo (vip/estandar): ");
        String nuevoTipo = sc.nextLine();
        
        System.out.print("Nueva ciudad: ");
        String nuevaCiudad = sc.nextLine();
        
        System.out.print("Nueva calle: ");
        String nuevaCalle = sc.nextLine();
        
        System.out.print("Nueva referencia: ");
        String nuevaReferencia = sc.nextLine();

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_actualizarCliente(?, ?, ?, ?, ?, ?, ?) }")) {

            cs.setInt(1, id);
            cs.setString(2, nuevoNombre);
            cs.setInt(3, nuevoTelefono);
            cs.setString(4, nuevoCorreo);
            cs.setString(5, nuevoTipo);
            cs.setString(6, nuevaCiudad);
            cs.setString(7, nuevaCalle);
            cs.setString(8, nuevaReferencia);

            cs.execute();
            System.out.println("Cliente actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al actualizar cliente: " + e.getMessage());
        }
    }

    public static void eliminarCliente() {
        System.out.print("\nIngrese el ID del cliente a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_eliminarCliente(?) }")) {

            cs.setInt(1, id);
            cs.execute();
            System.out.println("Cliente eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general al eliminar cliente: " + e.getMessage());
        }
    }

    public static void consultarClientes() {
        System.out.println("\n--- Listado de Clientes ---");

        try (Connection conn = ConexionBD.getConnection();
             CallableStatement cs = conn.prepareCall("{ CALL sp_consultarClientes() }");
             ResultSet rs = cs.executeQuery()) {

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
        } catch (Exception e) {
            System.out.println("Error general al consultar clientes: " + e.getMessage());
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

