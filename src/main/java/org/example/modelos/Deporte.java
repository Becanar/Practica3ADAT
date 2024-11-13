package org.example.modelos;

import java.util.Objects;

public class Deporte {

	private String nombreDeporte;

	public Deporte(String nombre) {
		this.nombreDeporte=nombre;
	}

	public String getNombreDeporte() {
		return nombreDeporte;
	}

	public void setNombreDeporte(String nombreDeporte) {
		this.nombreDeporte = nombreDeporte;
	}

	@Override
	public String toString() {
		return this.nombreDeporte;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreDeporte);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deporte other = (Deporte) obj;
		return Objects.equals(nombreDeporte, other.nombreDeporte);
	}
	
}
