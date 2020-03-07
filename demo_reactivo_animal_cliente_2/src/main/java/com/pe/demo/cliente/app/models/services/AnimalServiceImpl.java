package com.pe.demo.cliente.app.models.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.pe.demo.cliente.app.models.Animal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class AnimalServiceImpl implements AnimalService{
	

	/*
	@Autowired 
	private WebClient client;
	 * */

	@Autowired 
	private WebClient.Builder client;

	@Override
	public Flux<Animal> verTodos() {
		// TODO Auto-generated method stub
		return client.build().get()
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.flatMapMany(response-> response.bodyToFlux(Animal.class));
	}

	@Override
	public Mono<Animal> verId(String id) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		return client.build().get().uri("/{id}",params)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.flatMap(response-> response.bodyToMono(Animal.class));
	}

	@Override
	public Mono<Animal> guardar(Animal animal) {
		// TODO Auto-generated method stub
		return client.build().post()
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject(animal))
				.retrieve()
				.bodyToMono(Animal.class);
	}

	@Override
	public Mono<Animal> actualizar(String id, Animal animal) {
		// TODO Auto-generated method stub
		return client.build().put().uri("/{id}",Collections.singletonMap("id", id))
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject(animal))
				.retrieve()
				.bodyToMono(Animal.class);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		// TODO Auto-generated method stub
		return client.build().delete().uri("/{id}",Collections.singletonMap("id", id))
				.retrieve()
				.bodyToMono(Void.class);
	}

	@Override
	public Mono<Animal> upload(FilePart file, String id) {
		MultipartBodyBuilder parts=new MultipartBodyBuilder();
		parts.asyncPart("foto", file.content(), DataBuffer.class).headers(h-> {
			h.setContentDispositionFormData("file", file.filename());
		});
		return client.build().post()
				.uri("/upload/{id}",Collections.singletonMap("id", id))
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.syncBody(parts.build())
				.retrieve()
				.bodyToMono(Animal.class);
	}

}
