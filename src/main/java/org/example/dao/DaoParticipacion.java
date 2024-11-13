package org.example.dao;

import org.example.bd.ConectorBD;
import org.example.modelos.Participacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoParticipacion {

	private static Connection conection;

	public static void aniadirParticipacion(int idDeportista,int idEvento,int idEquipo,int edad,String medalla) {
		conection= ConectorBD.getConnection();
		String insertar="INSERT INTO Participacion (id_deportista,id_evento,id_equipo,edad,medalla) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(insertar);
			pstmt.setInt(1,idDeportista);
			pstmt.setInt(2, idEvento);
			pstmt.setInt(3,idEquipo);
			pstmt.setInt(4, edad);
			pstmt.setString(5, medalla);
			pstmt.executeUpdate();
			conection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean existeIdParticipacion(int idDeportista,int idEvento) {
		conection= ConectorBD.getConnection();
		String buscar="SELECT id_equipo FROM Participacion WHERE id_deportista=? AND id_evento=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(buscar);
			pstmt.setInt(1, idDeportista);
			pstmt.setInt(2, idEvento);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				conection.commit();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<String> darIdEvento(int idDeportista) {
		conection= ConectorBD.getConnection();
		String select="SELECT id_evento FROM Participacion WHERE id_deportista=?";
		ArrayList<String> lst=new ArrayList<String>();
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1, idDeportista);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				conection.commit();
				lst.add(rs.getString("id_evento"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static ArrayList<String> darIdDeportista(int idEvento) {
		conection= ConectorBD.getConnection();
		String select="SELECT id_deportista FROM Participacion WHERE id_evento=?";
		ArrayList<String> lst=new ArrayList<String>();
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1, idEvento);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				conection.commit();
				lst.add(rs.getString("id_deportista"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static Participacion crearModeloParticipacion(int idDeportista, int idEvento) {
		conection= ConectorBD.getConnection();
		String select="SELECT id_equipo,edad,medalla FROM Participacion WHERE id_deportista=? AND id_evento=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(select);
			pstmt.setInt(1,idDeportista);
			pstmt.setInt(2, idEvento);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				conection.commit();
				return new Participacion(DaoDeportista.crearModeloDeportista(idDeportista+""),DaoEvento.crearPorId(idEvento),DaoEquipo.crearModeloEquipo(rs.getInt("id_equipo")), rs.getInt("edad"),rs.getString("medalla"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void editarMedalla(int idDeportista,int idEvento,String nuevaMedalla) {
		conection= ConectorBD.getConnection();
		String update="UPDATE Participacion SET medalla=? WHERE id_deportista=? AND id_evento=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(update);
			pstmt.setString(1,nuevaMedalla);
			pstmt.setInt(2,idDeportista);
			pstmt.setInt(3, idEvento);
			pstmt.executeUpdate();
			conection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void eliminarParticipacion(int idDeportista,int idEvento) {
		conection= ConectorBD.getConnection();
		String delete="DELETE FROM Participacion WHERE id_deportista=? AND id_evento=?";
		try {
			PreparedStatement pstmt;
			pstmt=conection.prepareStatement(delete);
			pstmt.setInt(1,idDeportista);
			pstmt.setInt(2, idEvento);
			pstmt.executeUpdate();
			conection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
