package org.example.bd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConectorBD {
	private static Connection connection;

    public ConectorBD() {}

	public static Connection getConnection()  {
        try {
            if (connection == null || connection.isClosed()) {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                connection = DriverManager.getConnection(url, props);

                /*
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                System.out.println("--- Datos de conexión ------------------------------------------");
                System.out.printf("Base de datos: %s%n", databaseMetaData.getDatabaseProductName());
                System.out.printf("  Versión: %s%n", databaseMetaData.getDatabaseProductVersion());
                System.out.printf("Driver: %s%n", databaseMetaData.getDriverName());
                System.out.printf("  Versión: %s%n", databaseMetaData.getDriverVersion());
                System.out.println("----------------------------------------------------------------");
                */
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
	}

	public static void closeConexion() throws SQLException {

		if (connection != null && !connection.isClosed()) {
			connection.close();
			System.out.println("Conexión cerrada correctamente.");
		}
	}

	public static Properties loadProperties() throws FileNotFoundException {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		try {
			Connection conn = ConectorBD.getConnection();
			System.out.println("Conexión establecida.");
			ConectorBD.closeConexion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
