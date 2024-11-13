package org.example.dao;

import org.example.bd.ConectorBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DaoBD {

	private static Connection conection;

	public static void crearBBDD(String ruta) {
		System.out.println(ruta);
		conection = ConectorBD.getConnection();
		File archivoCSV = new File(ruta);

		String crearSchema = "DROP DATABASE IF EXISTS `olimpiadas`;";
		String crearDB = "CREATE DATABASE IF NOT EXISTS `olimpiadas` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;";
		String usarDB = "USE `olimpiadas`;";
		String setSqlMode = "SET SQL_MODE = \"NO_AUTO_VALUE_ON_ZERO\";";
		String setAutocommit = "SET AUTOCOMMIT = 0;";
		String startTransaction = "START TRANSACTION;";


		String crearTablaDeporte="CREATE TABLE `Deporte` (\r\n"
				+ "	`id_deporte` int(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "	`nombre` varchar(100)  NOT NULL,\r\n"
				+ "	PRIMARY KEY (`id_deporte`)\r\n"
				+ ") ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARACTER SET = latin1 COLLATE = latin1_spanish_ci;";
		String crearTablaDeportista="CREATE TABLE `Deportista` (\r\n"
				+ "	`id_deportista` int(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "	`nombre` varchar(150)  NOT NULL,\r\n"
				+ "	`sexo` enum('M', 'F')  NOT NULL,\r\n"
				+ "	`peso` int(11) DEFAULT NULL,\r\n"
				+ "	`altura` int(11) DEFAULT NULL,\r\n"
				+ "	PRIMARY KEY (`id_deportista`)\r\n"
				+ ") ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARACTER SET = latin1 COLLATE = latin1_spanish_ci;";
		String crearTablaEquipo="CREATE TABLE `Equipo` (\r\n"
				+ "	`id_equipo` int(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "	`nombre` varchar(50)  NOT NULL,\r\n"
				+ "	`iniciales` varchar(3)  NOT NULL,\r\n"
				+ "	PRIMARY KEY (`id_equipo`)\r\n"
				+ ") ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARACTER SET = latin1 COLLATE = latin1_spanish_ci;";
		String crearTablaOlimpiada="CREATE TABLE `Olimpiada` (\r\n"
				+ "	`id_olimpiada` int(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "	`nombre` varchar(11)  NOT NULL,\r\n"
				+ "	`anio` smallint(6) NOT NULL,\r\n"
				+ "	`temporada` enum('Summer', 'Winter')  NOT NULL,\r\n"
				+ "	`ciudad` varchar(50)  NOT NULL ,\r\n"
				+ "    PRIMARY KEY (`id_olimpiada`)\r\n"
				+ ") ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARACTER SET = latin1 COLLATE = latin1_spanish_ci;";
		String crearTablaEvento="CREATE TABLE `Evento` (\r\n"
				+ "	`id_evento` int(11) NOT NULL AUTO_INCREMENT,\r\n"
				+ "	`nombre` varchar(150) NOT NULL,\r\n"
				+ "	`id_olimpiada` int(11) NOT NULL,\r\n"
				+ "	`id_deporte` int(11) NOT NULL,\r\n"
				+ "	PRIMARY KEY (`id_evento`),\r\n"
				+ "	CONSTRAINT `FK_Evento_Deporte` FOREIGN KEY (`id_deporte`) REFERENCES `Deporte` (`id_deporte`),\r\n"
				+ "	CONSTRAINT `FK_Evento_Olimpiada` FOREIGN KEY (`id_olimpiada`) REFERENCES `Olimpiada` (`id_olimpiada`)\r\n"
				+ ") ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARACTER SET = latin1 COLLATE = latin1_spanish_ci;";
		String crearTablaParticipacion="CREATE TABLE `Participacion` (\r\n"
				+ "	`id_deportista` int(11) NOT NULL,\r\n"
				+ "	`id_evento` int(11) NOT NULL,\r\n"
				+ "	`id_equipo` int(11) NOT NULL,\r\n"
				+ "	`edad` tinyint(4) DEFAULT NULL,\r\n"
				+ "	`medalla` varchar(6) DEFAULT NULL,\r\n"
				+ "    PRIMARY KEY (`id_deportista`, `id_evento`),\r\n"
				+ "	CONSTRAINT `FK_Participacion_Deportista` FOREIGN KEY (`id_deportista`) REFERENCES `Deportista` (`id_deportista`),\r\n"
				+ "    CONSTRAINT `FK_Participacion_Equipo` FOREIGN KEY (`id_equipo`) REFERENCES `Equipo` (`id_equipo`),\r\n"
				+ "    CONSTRAINT `FK_Participacion_Evento` FOREIGN KEY (`id_evento`) REFERENCES `Evento` (`id_evento`)\r\n"
				+ ") ENGINE = InnoDB DEFAULT CHARACTER SET = latin1 COLLATE = latin1_spanish_ci;";

		try {
			ejecutarUpdate(crearSchema);
			ejecutarUpdate(crearDB);
			ejecutarUpdate(usarDB);
			ejecutarUpdate(setSqlMode);
			ejecutarUpdate(setAutocommit);
			ejecutarUpdate(startTransaction);
			ejecutarUpdate(crearTablaDeporte);
			ejecutarUpdate(crearTablaDeportista);
			ejecutarUpdate(crearTablaEquipo);
			ejecutarUpdate(crearTablaOlimpiada);
			ejecutarUpdate(crearTablaEvento);
			ejecutarUpdate(crearTablaParticipacion);

			if (archivoCSV.isFile() && ruta.endsWith(".csv")) {
				try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
					String linea = br.readLine();
					// Cambiar el csv a mano para quitar todas las "
					if (linea.equals("ID,Name,Sex,Age,Height,Weight,Team,NOC,Games,Year,Season,City,Sport,Event,Medal")) {
						linea = br.readLine();
						while (linea != null) {
							String[] leido = linea.split(",");
							if (leido[3].equals("NA")) { leido[3] = "-1"; }
							if (leido[4].equals("NA")) { leido[4] = "-1"; }
							if (leido[5].equals("NA")) { leido[5] = "-1"; }
							leido[5] = Math.round(Float.parseFloat(leido[5])) + "";

							// Aquí se verifican los datos y se insertan si no existen
							if (DaoDeportista.conseguirIdDeportista(leido[1], leido[2].charAt(0), Float.parseFloat(leido[5]), Integer.parseInt(leido[4])) == null) {
								DaoDeportista.aniadirDeportista(leido[1], leido[2].charAt(0), Float.parseFloat(leido[5]), Integer.parseInt(leido[4]));
							}
							if (DaoDeporte.conseguirIdDeporte(leido[12]) == null) {
								DaoDeporte.aniadirDeporte(leido[12]);
							}
							if (DaoEquipo.conseguirIdEquipo(leido[6], leido[7]) == null) {
								DaoEquipo.aniadirEquipo(leido[6], leido[7]);
							}
							if (DaoOlimpiada.conseguirIdOlimpiada(leido[8], Integer.parseInt(leido[9]), leido[10], leido[11]) == null) {
								DaoOlimpiada.aniadirOlimpiada(leido[8], Integer.parseInt(leido[9]), leido[10], leido[11]);
							}
							if (DaoEvento.conseguirIdEvento(leido[13], Integer.parseInt(DaoOlimpiada.conseguirIdOlimpiada(leido[8], Integer.parseInt(leido[9]), leido[10], leido[11])),
									Integer.parseInt(DaoDeporte.conseguirIdDeporte(leido[12]))) == null) {
								DaoEvento.aniadirEvento(leido[13], Integer.parseInt(DaoOlimpiada.conseguirIdOlimpiada(leido[8], Integer.parseInt(leido[9]), leido[10], leido[11])),
										Integer.parseInt(DaoDeporte.conseguirIdDeporte(leido[12])));
							}
							if (!DaoParticipacion.existeIdParticipacion(Integer.parseInt(DaoDeportista.conseguirIdDeportista(leido[1], leido[2].charAt(0),
									Float.parseFloat(leido[5]), Integer.parseInt(leido[4]))), Integer.parseInt(DaoEvento.conseguirIdEvento(leido[13],
									Integer.parseInt(DaoOlimpiada.conseguirIdOlimpiada(leido[8], Integer.parseInt(leido[9]), leido[10], leido[11])),
									Integer.parseInt(DaoDeporte.conseguirIdDeporte(leido[12])))))) {
								DaoParticipacion.aniadirParticipacion(Integer.parseInt(DaoDeportista.conseguirIdDeportista(leido[1], leido[2].charAt(0),
												Float.parseFloat(leido[5]), Integer.parseInt(leido[4]))), Integer.parseInt(DaoEvento.conseguirIdEvento(leido[13],
												Integer.parseInt(DaoOlimpiada.conseguirIdOlimpiada(leido[8], Integer.parseInt(leido[9]), leido[10], leido[11])),
												Integer.parseInt(DaoDeporte.conseguirIdDeporte(leido[12])))),
										Integer.parseInt(DaoEquipo.conseguirIdEquipo(leido[6], leido[7])), Integer.parseInt(leido[3]), leido[14]);
							}
							linea = br.readLine();
						}
						System.out.println("La carga de la información se ha realizado correctamente");
					}
				} catch (FileNotFoundException e) {
					System.out.println("El archivo csv no existe");
				} catch (IOException e) {
					System.out.println("Ha ocurrido algún error en la carga");
				}
			} else {
				System.out.println("El archivo csv no existe o no es válido");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	static void ejecutarUpdate(String sentencia) throws SQLException {
		conection=ConectorBD.getConnection();
		PreparedStatement pstmt = conection.prepareStatement(sentencia);
		pstmt.executeUpdate();
	}

}