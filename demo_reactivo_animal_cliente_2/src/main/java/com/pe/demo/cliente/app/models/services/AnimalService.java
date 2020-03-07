package com.pe.demo.cliente.app.models.services;

import org.springframework.http.codec.multipart.FilePart;

import com.pe.demo.cliente.app.models.Animal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AnimalService {
	
	public Flux<Animal> verTodos();
	public Mono<Animal> verId(String id);
	public Mono<Animal> guardar(Animal animal);
	public Mono<Animal> actualizar(String id,Animal animal);
	public Mono<Void> eliminar(String id);
	public Mono<Animal> upload(FilePart file,String id);

}
