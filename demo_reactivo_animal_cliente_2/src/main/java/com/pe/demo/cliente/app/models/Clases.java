package com.pe.demo.cliente.app.models;

import javax.validation.constraints.NotEmpty;

public class Clases {
	

	private String IdClase;
	private String nombre;
	
	public Clases() {
		
	}
	
	
	public Clases(String nombre) {
		this.nombre = nombre;
	}
	
	public String getIdClase() {
		return IdClase;
	}
	public void setIdClase(String idClase) {
		IdClase = idClase;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
