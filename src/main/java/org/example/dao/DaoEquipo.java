package org.example.dao;

import org.example.bd.ConectorBD;
import org.example.modelos.Equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoEquipo {

	private static Connection conection;

	public static void aniadirEquipo(String nombre, String iniciales) {
		conection= ConectorBD.getConnection();
		String insertar="INSERT INTO Equipo (nombre,iniciales) VALUES (?,?)";
		
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(insertar);
			pstmt.setString(1,nombre);
			pstmt.setString(2,iniciales);
			pstmt.executeUpdate();
			conection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String conseguirIdEquipo(String nombre, String iniciales) {
		conection= ConectorBD.getConnection();
		String select="SELECT id_equipo FROM Equipo WHERE nombre=? AND iniciales=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setString(1,nombre);
			pstmt.setString(2, iniciales);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_equipo");
				conection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Equipo crearModeloEquipo(int id) {
		conection= ConectorBD.getConnection();
		String select="SELECT nombre,iniciales FROM Equipo WHERE id_equipo=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				conection.commit();
				return new Equipo(rs.getString("nombre"), rs.getString("iniciales"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
