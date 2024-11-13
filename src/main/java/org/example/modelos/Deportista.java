package org.example.modelos;

import java.util.Objects;

public class Deportista {

	private String nombreDeportista;
	private char sexo;
	private int altura;
	private float peso;

	public Deportista(String nombreDeportista, char sexo, int altura, float peso) {
		this.nombreDeportista = nombreDeportista;
		this.sexo = sexo;
		this.altura = altura;
		this.peso = peso;
	}

	public String getNombreDeportista() {
		return nombreDeportista;
	}

	public void setNombreDeportista(String nombreDeportista) {
		this.nombreDeportista = nombreDeportista;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return this.nombreDeportista+","+this.sexo+","+this.altura+","+this.peso;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Deportista that = (Deportista) o;
		return sexo == that.sexo && altura == that.altura && Float.compare(peso, that.peso) == 0 && Objects.equals(nombreDeportista, that.nombreDeportista);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreDeportista, sexo, altura, peso);
	}
}
