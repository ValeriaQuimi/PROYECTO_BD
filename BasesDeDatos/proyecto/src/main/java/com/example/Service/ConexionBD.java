package com.example.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Cargar el driver JDBC manualmente
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Datos de conexión
            String url = ""; //url de la base de datos
            String user = ""; // tu usuario 
            String password = ""; // tu contraseña

            // Intentar conectar
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver JDBC:");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection conexion = getConnection();
        if (conexion != null) {
            System.out.println("Conexión exitosa a la base de datos.");
        } else {
            System.out.println("No se pudo establecer conexión.");
        }
    }

}
