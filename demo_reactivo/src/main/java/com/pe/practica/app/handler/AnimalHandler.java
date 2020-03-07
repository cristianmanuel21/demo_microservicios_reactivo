package com.pe.practica.app.handler;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.pe.practica.app.models.documents.Animal;
import com.pe.practica.app.models.services.AnimalService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AnimalHandler {
	
	@Autowired
	private AnimalService service;
	
	@Value("{$config.uploads.path}")
	private String path;
	
	@Autowired
	private Validator validator;
	
	public Mono<ServerResponse> getAll(ServerRequest request){
		return ServerResponse.ok()
			   .contentType(MediaType.APPLICATION_JSON_UTF8)
			   .body(service.findAll(),Animal.class);
	}
	
	
	public Mono<ServerResponse> getId(ServerRequest request){
		String id=request.pathVariable("id");
		
		return service.findbyId(id).flatMap(b -> {
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(b));
		}).switchIfEmpty(ServerResponse.notFound().build());
		
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request){
		String id=request.pathVariable("id");
		return service.findbyId(id).flatMap(k -> {
			return service.deleteAnimal(k).then(ServerResponse.noContent().build());
		}).switchIfEmpty(ServerResponse.notFound().build());
	}
	
	
	
	public Mono<ServerResponse> crearFinal(ServerRequest request){
		Mono<Animal> animal=request.bodyToMono(Animal.class);
		return animal.flatMap(x -> {
			Errors errors=new BeanPropertyBindingResult(x,Animal.class.getName());
			validator.validate(x, errors);
			if(errors.hasErrors()) {
				return Flux.fromIterable(errors.getFieldErrors())
						.map(error-> "el campo "+error.getField()+" "+error.getDefaultMessage())
						.collectList()
						.flatMap( lista -> ServerResponse.badRequest().body(BodyInserters.fromObject(lista)));
			}else {
				if(x.getFechaRegistro()==null) {
					x.setFechaRegistro(new Date());
				}
				return service.saveAnimal(x).flatMap(z-> ServerResponse
						.created(URI.create("/api/v2/animales"+z.getIdAnimal()))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(BodyInserters.fromObject(x)));
			}
		});
	}
	
	
	public Mono<ServerResponse> actualizar(ServerRequest request){
		String id=request.pathVariable("id");
		Mono<Animal> animal=request.bodyToMono(Animal.class);
		Mono<Animal> animaldb=service.findbyId(id);
		
		return animaldb.zipWith(animal , (db, req) -> {
			db.setNombre(req.getNombre());
			db.setEdad(req.getEdad());
			db.setClase(req.getClase());
			return db;
		}).flatMap(z -> ServerResponse.created(URI.create("/api/v2/animales/"+z.getIdAnimal() ))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.saveAnimal(z),Animal.class) )
			.switchIfEmpty(ServerResponse.notFound().build());
	
	}
	
	
	
	public Mono<ServerResponse> upload (ServerRequest request){
		String id=request.pathVariable("id");
		
		return request.multipartData().map(m -> m.toSingleValueMap().get("file"))
				
				.cast(FilePart.class)
				
				.flatMap(file -> service.findbyId(id).flatMap(k -> {
					k.setFoto(UUID.randomUUID().toString()+"-"+file.filename()
					.replace(" ","")
					.replace(":","")
					.replace("\\",""));
					return file.transferTo(new File(path+k.getFoto())).then(service.saveAnimal(k));
				}))
				
				.flatMap(z-> ServerResponse.created(URI.create("/api/v2/animales/"+z.getIdAnimal()))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(BodyInserters.fromObject(z)))
				
				.switchIfEmpty(ServerResponse.notFound().build());
	
	}
	
}
