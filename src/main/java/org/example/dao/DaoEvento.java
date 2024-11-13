package org.example.dao;

import org.example.bd.ConectorBD;
import org.example.modelos.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoEvento {

	private static Connection conection;

	public static void aniadirEvento(String nombreEvento,int idOlimpiada,int idDeporte) {
		conection= ConectorBD.getConnection();
		String insertar="INSERT INTO Evento (nombre,id_olimpiada,id_deporte) VALUES (?,?,?)";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(insertar);
			pstmt.setString(1,nombreEvento);
			pstmt.setInt(2, idOlimpiada);
			pstmt.setInt(3, idDeporte);
			pstmt.executeUpdate();
			conection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String conseguirIdEvento(String nombreEvento,int idOlimpiada,int idDeporte) {
		conection= ConectorBD.getConnection();
		String select="SELECT id_evento FROM Evento WHERE nombre=? AND id_olimpiada=? AND id_deporte=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setString(1,nombreEvento);
			pstmt.setInt(2, idOlimpiada);
			pstmt.setInt(3, idDeporte);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString("id_evento");
				conection.commit();
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Evento crearPorId(int id) {
		conection= ConectorBD.getConnection();
		String select="SELECT nombre,id_deporte,id_olimpiada FROM Evento WHERE id_evento=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				conection.commit();
				return new Evento(rs.getString("nombre"), DaoDeporte.crearModeloDeporte(rs.getInt("id_deporte")),DaoOlimpiada.crearModeloOlimpiada(rs.getInt("id_olimpiada")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Evento> crearListaModelosPorDeporteYOlimpiada(int idDeporte, int idOlimpiada) {
		conection= ConectorBD.getConnection();
		String select="SELECT nombre FROM Evento WHERE id_deporte=? AND id_olimpiada=?";
		ArrayList<Evento>lst=new ArrayList<Evento>();
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1, idDeporte);
			pstmt.setInt(2, idOlimpiada);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				conection.commit();
				Evento evento=new Evento(rs.getString("nombre"),DaoDeporte.crearModeloDeporte(idDeporte),DaoOlimpiada.crearModeloOlimpiada(idOlimpiada));
				if(!lst.contains(evento)) {
					lst.add(evento);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;	
	}

	public static ArrayList<Evento> crearListaModelosPorLstId(ArrayList<String>lstId){
		conection= ConectorBD.getConnection();
		ArrayList<Evento>lst=new ArrayList<Evento>();
		String select="SELECT nombre,id_deporte,id_olimpiada FROM Evento where id_evento=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			for(String id:lstId) {
				pstmt.setInt(1,Integer.parseInt(id));
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					conection.commit();
					Evento evento=new Evento(rs.getString("nombre"),DaoDeporte.crearModeloDeporte(rs.getInt("id_deporte")),DaoOlimpiada.crearModeloOlimpiada(rs.getInt("id_olimpiada")));
					lst.add(evento);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}
	
}
