package com.example.Service;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EntregaService {
    private static Scanner sc = new Scanner(System.in);

    public static void registrarEntrega() {
    try (Connection conn = ConexionBD.getConnection()) {
        System.out.println("\n=== REGISTRO DE ENTREGA ===");

        // Fecha de entrega
        System.out.print("Fecha de entrega (YYYY-MM-DD): ");
        String fechaStr = sc.nextLine();
        Date fechaEntrega = Date.valueOf(fechaStr);

        // Estado
        System.out.print("Estado de entrega (pendiente, entregada): ");
        String estado = sc.nextLine();

        // Selección de repartidor
        int idRepartidor = seleccionarRepartidor(conn);
        if (idRepartidor == -1) return;

        // Selección de pedido
        int numOrden = seleccionarPedido(conn);
        if (numOrden == -1) return;

        // Llamada al procedimiento almacenado
        String sql = "{CALL sp_insertar_entrega(?, ?, ?, ?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setDate(1, fechaEntrega);
            cs.setString(2, estado);
            cs.setInt(3, idRepartidor);
            cs.setInt(4, numOrden);

            cs.execute();
            System.out.println("Entrega registrada correctamente mediante SP.");
        }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    public static void consultarEntregas() {
        System.out.println("\n=== LISTA DE ENTREGAS ===");

        String sql = """
            SELECT e.numEntrega, e.fechaEntrega, e.estadoEntrega,
                   r.nombre AS nombreRepartidor,
                   p.numOrden AS pedido
            FROM entrega e
            JOIN repartidor r ON e.idRepartidor = r.idRepartidor
            JOIN pedido p ON e.numOrden = p.numOrden
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int numEntrega = rs.getInt("numEntrega");
                Date fecha = rs.getDate("fechaEntrega");
                String estado = rs.getString("estadoEntrega");
                String repartidor = rs.getString("nombreRepartidor");
                int pedido = rs.getInt("pedido");

                System.out.printf("Entrega #%d | Fecha: %s | Estado: %s | Repartidor: %s | Pedido: #%d\n",
                        numEntrega, fecha, estado, repartidor, pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar entregas: " + e.getMessage());
        }
    }

    public static void eliminarEntrega() {
    System.out.print("Ingrese el número de entrega a eliminar: ");
    int numEntrega = Integer.parseInt(sc.nextLine());

    String sql = "{CALL sp_eliminar_entrega(?)}";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, numEntrega);

        cs.execute();
        System.out.println("Entrega eliminada correctamente mediante SP.");

    } catch (SQLException e) {
        System.out.println("Error al eliminar entrega: " + e.getMessage());
    }
}

    public static void editarEntrega() {
    System.out.print("Ingrese el número de entrega a editar: ");
    int numEntrega = Integer.parseInt(sc.nextLine());

    String verificarSql = "SELECT * FROM entrega WHERE numEntrega = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement verificarPs = conn.prepareStatement(verificarSql)) {

        verificarPs.setInt(1, numEntrega);
        try (ResultSet rs = verificarPs.executeQuery()) {
            if (!rs.next()) {
                System.out.println("Entrega no encontrada.");
                return;
            }
        }

        // Nuevos datos
        System.out.print("Nueva fecha (YYYY-MM-DD): ");
        Date nuevaFecha = Date.valueOf(sc.nextLine());

        System.out.print("Nuevo estado (pendiente, entregada): ");
        String nuevoEstado = sc.nextLine();

        // Nuevo repartidor
        int nuevoRepartidor = seleccionarRepartidor(conn);
        if (nuevoRepartidor == -1) return;

        // Nuevo pedido
        int nuevoPedido = seleccionarPedido(conn);
        if (nuevoPedido == -1) return;

        // Llamada al procedimiento almacenado
        String sql = "{CALL sp_actualizar_entrega(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, numEntrega);
            cs.setString(2, nuevoEstado);
            cs.setDate(3, nuevaFecha);
            cs.setInt(4, nuevoRepartidor);
            cs.setInt(5, nuevoPedido);

            cs.execute();
            System.out.println("Entrega actualizada correctamente mediante SP.");
        }

    } catch (SQLException e) {
        System.out.println("Error al editar entrega: " + e.getMessage());
    }
}

    // Métodos auxiliares
    private static int seleccionarRepartidor(Connection conn) throws SQLException {
        String sql = "SELECT idRepartidor, nombre FROM repartidor";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int i = 1;
            while (rs.next()) {
                int id = rs.getInt("idRepartidor");
                String nombre = rs.getString("nombre");
                System.out.printf("%d. %s (ID: %d)\n", i++, nombre, id);
                ids.add(id);
            }
        }

        if (ids.isEmpty()) {
            System.out.println("No hay repartidores disponibles.");
            return -1;
        }

        System.out.print("Seleccione un repartidor: ");
        int opcion = Integer.parseInt(sc.nextLine());

        if (opcion < 1 || opcion > ids.size()) return -1;
        return ids.get(opcion - 1);
    }

    private static int seleccionarPedido(Connection conn) throws SQLException {
        String sql = "SELECT numOrden FROM pedido";
        List<Integer> pedidos = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int i = 1;
            while (rs.next()) {
                int numOrden = rs.getInt("numOrden");
                System.out.printf("%d. Pedido #%d\n", i++, numOrden);
                pedidos.add(numOrden);
            }
        }

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos disponibles.");
            return -1;
        }

        System.out.print("Seleccione un pedido: ");
        int opcion = Integer.parseInt(sc.nextLine());

        if (opcion < 1 || opcion > pedidos.size()) return -1;
        return pedidos.get(opcion - 1);
    }
}
