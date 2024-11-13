package org.example.modelos;

import java.util.Objects;

public class Equipo {

	private String nombreEquipo;
	private String iniciales;

	public Equipo(String nombreEquipo, String iniciales) {
		this.nombreEquipo = nombreEquipo;
		this.iniciales = iniciales;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}

	@Override
	public String toString() {
		return "Equipo{" +
				"nombreEquipo='" + nombreEquipo + '\'' +
				", iniciales='" + iniciales + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Equipo equipo = (Equipo) o;
		return Objects.equals(nombreEquipo, equipo.nombreEquipo) && Objects.equals(iniciales, equipo.iniciales);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreEquipo, iniciales);
	}
}
