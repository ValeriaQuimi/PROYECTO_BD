package com.example.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reporte {

    public static void mostrarReporteCategoriasMasVendidas() {
        String sql = "SELECT * FROM reporteCategoriaMasVendida";

        try (Connection conn = ConexionBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== Reporte de Categorías Más Vendidas ===");
            System.out.printf("%-25s %-20s %-20s\n", "Categoría", "Total Productos", "Número Clientes");

            while (rs.next()) {
                String categoria = rs.getString("Categoria");
                int totalCantidad = rs.getInt("total_cantidad_pedida");
                int numClientes = rs.getInt("numero_clientes");

                System.out.printf("%-25s %-20d %-20d\n", categoria, totalCantidad, numClientes);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
