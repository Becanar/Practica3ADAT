package org.example.dao;

import org.example.bd.ConectorBD;
import org.example.modelos.Deporte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoDeporte {

	private static Connection conection;

	public static void aniadirDeporte(String nombreDeporte) {
		conection= ConectorBD.getConnection();
		String insertar="INSERT INTO Deporte (nombre) VALUES (?)";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(insertar);
			pstmt.setString(1, nombreDeporte);
			pstmt.executeUpdate();
			conection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String conseguirIdDeporte(String nombre) {
		conection= ConectorBD.getConnection();
		String select="SELECT id_deporte FROM Deporte WHERE nombre=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setString(1,nombre);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_deporte");
				conection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Deporte crearModeloDeporte(int id) {
		conection= ConectorBD.getConnection();
		String select="SELECT nombre FROM Deporte WHERE id_deporte=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				conection.commit();
				return new Deporte(rs.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Deporte> listaDeportesPorOlimpiada(int idOlimpiada){
		conection= ConectorBD.getConnection();
		ArrayList<Deporte>lst=new ArrayList<Deporte>();
		String select="SELECT id_deporte FROM Evento WHERE id_olimpiada=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1, idOlimpiada);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				conection.commit();
				Deporte deporte=DaoDeporte.crearModeloDeporte(rs.getInt("id_deporte"));
				if(!lst.contains(deporte)) {
					lst.add(deporte);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}
	
}
