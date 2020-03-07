package com.pe.demo.cliente.app.models;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class Animal {
	
	private String idAnimal;

	private String nombre;

	private int edad;

	private String alias;
	private String foto;


	private Clases clase;
	private Date fechaRegistro;

	public Animal() {
	};

	public Animal(String nombre, int edad, String alias, Clases clase) {
		this.nombre = nombre;
		this.edad = edad;
		this.alias = alias;
		this.clase = clase;

	}

	public Animal(String nombre, int edad, String alias, Date fechaRegistro, Clases clase) {
		this.nombre = nombre;
		this.edad = edad;
		this.alias = alias;
		this.clase = clase;
		this.fechaRegistro = fechaRegistro;
	}

	public String getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(String idAnimal) {
		this.idAnimal = idAnimal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Clases getClase() {
		return clase;
	}

	public void setClase(Clases clase) {
		this.clase = clase;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
