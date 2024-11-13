package org.example.modelos;

import java.util.Objects;

public class Participacion {

	private Deportista deportista;
	private Evento evento;
	private Equipo equipo;
	private int edad;
	private String medalla;

	public Participacion(Deportista deportista, Evento evento, Equipo equipo, int edad, String medalla) {
		this.deportista=deportista;
		this.evento=evento;
		this.equipo = equipo;
		this.edad = edad;
		this.medalla = medalla;
	}

	public Deportista getDeportista() {
		return deportista;
	}

	public void setDeportista(Deportista deportista) {
		this.deportista = deportista;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getMedalla() {
		return medalla;
	}

	public void setMedalla(String medalla) {
		this.medalla = medalla;
	}

	@Override
	public String toString() {
		return this.deportista.getNombreDeportista()+","+this.deportista.getAltura()+","+this.deportista.getPeso()+
				","+this.edad+","+this.equipo.getNombreEquipo()+","+this.medalla;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Participacion that = (Participacion) o;
		return edad == that.edad && Objects.equals(deportista, that.deportista) && Objects.equals(evento, that.evento) && Objects.equals(equipo, that.equipo) && Objects.equals(medalla, that.medalla);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deportista, evento, equipo, edad, medalla);
	}
}
