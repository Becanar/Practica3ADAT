package org.example.dao;

import org.example.bd.ConectorBD;
import org.example.modelos.Deportista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoDeportista {

	private static Connection conection;

	public static void aniadirDeportista(String nombreDeportista,char sexo,float peso,int altura) {
		conection= ConectorBD.getConnection();
		String insertar="INSERT INTO Deportista (nombre,sexo,peso,altura) VALUES (?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt=conection.prepareStatement(insertar);
			pstmt.setString(1, nombreDeportista);
			pstmt.setString(2, sexo+"");
			pstmt.setFloat(3, peso);
			pstmt.setInt(4, altura);
			pstmt.executeUpdate();
			conection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String conseguirIdDeportista(String nombreDeportista,char sexo,float peso,int altura) {
		conection= ConectorBD.getConnection();
		String select="SELECT id_deportista FROM Deportista WHERE nombre=? AND sexo=? AND peso=? AND altura=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setString(1,nombreDeportista);
			pstmt.setString(2,sexo+"");
			pstmt.setFloat(3,peso);
			pstmt.setInt(4,altura);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_deportista");
				conection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Deportista crearModeloDeportista(String id) {
		conection= ConectorBD.getConnection();
		String select="SELECT nombre,sexo,peso,altura FROM Deportista WHERE id_deportista=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setString(1,id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				conection.commit();
				return new Deportista(rs.getString("nombre"), rs.getString("sexo").charAt(0), rs.getInt("altura"), rs.getInt("peso"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Deportista> buscarNombresDeportistas(String cadena){
		conection= ConectorBD.getConnection();
		ArrayList<Deportista>lst=new ArrayList<Deportista>();
		String select="SELECT id_deportista FROM Deportista WHERE nombre LIKE ?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setString(1,"%"+cadena+"%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				conection.commit();
				lst.add(DaoDeportista.crearModeloDeportista(rs.getInt("id_deportista")+""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;	
	}
	
}
