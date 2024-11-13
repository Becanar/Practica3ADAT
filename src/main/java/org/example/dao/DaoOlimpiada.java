package org.example.dao;

import org.example.bd.ConectorBD;
import org.example.modelos.Olimpiada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoOlimpiada {

	private static Connection conection;

	public static void aniadirOlimpiada(String nombre, int anio,String temporada,String ciudad) {
		conection= ConectorBD.getConnection();
		String insertar="INSERT INTO Olimpiada (nombre,anio,temporada,ciudad) VALUES (?,?,?,?)";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(insertar);
			pstmt.setString(1,nombre);
			pstmt.setInt(2, anio);
			pstmt.setString(3,temporada);
			pstmt.setString(4,ciudad);
			pstmt.executeUpdate();
			conection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String conseguirIdOlimpiada(String nombre, int anio,String temporada,String ciudad) {
		conection= ConectorBD.getConnection();
		String select="SELECT id_olimpiada FROM Olimpiada WHERE nombre=? AND anio=? AND temporada=? AND ciudad=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setString(1,nombre);
			pstmt.setInt(2, anio);
			pstmt.setString(3, temporada);
			pstmt.setString(4,ciudad);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_olimpiada");
				conection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Olimpiada crearModeloOlimpiada(int id) {
		conection= ConectorBD.getConnection();
		String select="SELECT nombre,anio,temporada,ciudad FROM Olimpiada WHERE id_olimpiada=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				conection.commit();
				return new Olimpiada(rs.getString("nombre"), rs.getInt("anio"),rs.getString("temporada"),rs.getString("ciudad"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Olimpiada> listaOlimpiadasPorTemporada(int temp){
		ArrayList<Olimpiada> lst=new ArrayList<Olimpiada>();
		String temporada="Winter";
		if(temp==2) {
			temporada="Summer";
		}
		conection= ConectorBD.getConnection();
		String select="SELECT id_olimpiada FROM Olimpiada WHERE temporada=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setString(1, temporada);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				conection.commit();
				lst.add(DaoOlimpiada.crearModeloOlimpiada(rs.getInt("id_olimpiada")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}
	
}
