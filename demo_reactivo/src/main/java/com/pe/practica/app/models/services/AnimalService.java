package com.pe.practica.app.models.services;

import com.pe.practica.app.models.documents.Animal;
import com.pe.practica.app.models.documents.Clases;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AnimalService {
	
	public Flux<Animal> findAll();
	public Mono<Animal> findbyId(String id);
	public Mono<Animal> saveAnimal(Animal animal);
	public Mono<Void> deleteAnimal(Animal animal);
	
	public Mono<Clases> saveClases(Clases clases);
	public Flux<Clases> findAllClases();
	public Mono<Clases> findByIdClases(String id);
	
	public Mono<Animal> findByAlias(String alias);
	public Mono<Clases> findByNombre(String nombre);
}
