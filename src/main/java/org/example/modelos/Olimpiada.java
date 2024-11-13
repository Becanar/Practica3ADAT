package org.example.modelos;

import java.util.Objects;

public class Olimpiada {

	private String nombreOlimpiada;
	private int anio;
	private String temporada;
	private String ciudad;

	public Olimpiada(String nombreOlimpiada, int anio, String temporada, String ciudad) {
		this.nombreOlimpiada = nombreOlimpiada;
		this.anio = anio;
		this.temporada = temporada;
		this.ciudad = ciudad;
	}

	public String getNombreOlimpiada() {
		return nombreOlimpiada;
	}

	public void setNombreOlimpiada(String nombreOlimpiada) {
		this.nombreOlimpiada = nombreOlimpiada;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	@Override
	public String toString() {
		return this.nombreOlimpiada+","+this.anio+","+this.temporada+","+this.ciudad;
	}
	@Override
	public int hashCode() {
		return Objects.hash(anio, ciudad, nombreOlimpiada, temporada);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Olimpiada other = (Olimpiada) obj;
		return anio == other.anio && Objects.equals(ciudad, other.ciudad)
				&& Objects.equals(nombreOlimpiada, other.nombreOlimpiada) && Objects.equals(temporada, other.temporada);
	}
}
