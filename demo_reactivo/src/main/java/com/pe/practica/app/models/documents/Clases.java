package com.pe.practica.app.models.documents;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clases")
public class Clases {
	

	@Id
	@NotEmpty
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
