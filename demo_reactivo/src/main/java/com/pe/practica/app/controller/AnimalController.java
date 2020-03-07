package com.pe.practica.app.controller;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.pe.practica.app.models.documents.Animal;
import com.pe.practica.app.models.services.AnimalService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/animales")
public class AnimalController {
	
	
	@Autowired
	private AnimalService service;
	
	@Value("${config.uploads.path}")
	private String path;
	
	/*@GetMapping
	public Flux<Animal> getAll(){
		return service.findAll(); 
	}*/
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Animal>>> verTodos(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll())
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Animal>> getbyIdAnimal(@PathVariable String id){
		return service.findbyId(id).map(x-> ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(x))
			.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@PostMapping
	public Mono<ResponseEntity<Animal>> guardar(@RequestBody Animal animal){
		if(animal.getFechaRegistro()==null) {
			animal.setFechaRegistro(new Date());
		}
		return service.saveAnimal(animal).map(z-> ResponseEntity
				.created(URI.create("/api/animales/".concat(z.getIdAnimal())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(z));
	}
	
	@PostMapping("/subir/{id}")
	public Mono<ResponseEntity<Animal>> subirArchivo(@PathVariable String id, @RequestPart FilePart file){
		return service.findbyId(id).flatMap(x-> {
			x.setFoto(UUID.randomUUID().toString()+"-"+file.filename()
			.replace(" " , "")
			.replace(";" , "")
			.replace("\\" , ""));
			return file.transferTo(new File(path+x.getFoto())).then(service.saveAnimal(x));
		}).map(p-> ResponseEntity.ok(p))
		.defaultIfEmpty(ResponseEntity.notFound().build());
		
	} 
	
	
	@PostMapping("/subev2")
	public Mono<ResponseEntity<Animal>> subirV2(@RequestPart FilePart file, Animal animal){
		if(animal.getFechaRegistro()==null) {
			animal.setFechaRegistro(new Date());
		}
		animal.setFoto(UUID.randomUUID().toString()+"-"+file.filename()
		.replace(" ","")
		.replace("\\","")
		.replace(":",""));
		
		return file.transferTo(new File(path+animal.getFoto())).
				then( service.saveAnimal(animal)
				.map(x-> ResponseEntity.created(URI.create("/api/animales"+x.getIdAnimal()))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(x)) );
			
	}

	
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Animal>> updateAnimal(@PathVariable String id,@RequestBody Animal animal ){
		return service.findbyId(id).flatMap(x->{
			x.setNombre(animal.getNombre());
			x.setEdad(animal.getEdad());
			x.setAlias(animal.getAlias());
			x.setClase(animal.getClase());
			return service.saveAnimal(x);
		}).
				map( z -> ResponseEntity
				.created(URI.create("/api/animales/".concat(z.getIdAnimal())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(z))
		.defaultIfEmpty(ResponseEntity.notFound().build());
				
	}
	

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminarAnimal(@PathVariable String id){
		return service.findbyId(id).flatMap(z->{
			return service.deleteAnimal(z).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}

