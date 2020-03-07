package com.pe.practica.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.pe.practica.app.models.documents.Animal;

import reactor.core.publisher.Mono;

public interface AnimalDao extends ReactiveMongoRepository<Animal,String>{
	
	
	
	public Mono<Animal> findByAlias(String alias);

}
