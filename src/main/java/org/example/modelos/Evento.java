package org.example.modelos;

import java.util.Objects;

public class Evento {

	private String nombreEvento;
	private Deporte deporte;
	private Olimpiada olimpiada;

	public Evento(String nombreEvento, Deporte deporte, Olimpiada olimpiada) {
		this.nombreEvento = nombreEvento;
		this.deporte = deporte;
		this.olimpiada = olimpiada;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public Olimpiada getOlimpiada() {
		return olimpiada;
	}

	public void setOlimpiada(Olimpiada olimpiada) {
		this.olimpiada = olimpiada;
	}

	@Override
	public String toString() {
		return this.nombreEvento+","+this.deporte.getNombreDeporte()+","+this.olimpiada.getNombreOlimpiada();
	}

	@Override
	public int hashCode() {
		return Objects.hash(deporte, nombreEvento, olimpiada);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		return Objects.equals(deporte, other.deporte) && Objects.equals(nombreEvento, other.nombreEvento)
				&& Objects.equals(olimpiada, other.olimpiada);
	}
	
	
	
}
