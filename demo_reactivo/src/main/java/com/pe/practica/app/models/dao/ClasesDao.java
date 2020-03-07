package com.pe.practica.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.pe.practica.app.models.documents.Clases;

import reactor.core.publisher.Mono;

public interface ClasesDao extends ReactiveMongoRepository<Clases,String>{
	
	public Mono<Clases> findByNombre(String nombre);

}
