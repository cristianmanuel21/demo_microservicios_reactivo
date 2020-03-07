package com.pe.demo.cliente.app.handler;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.pe.demo.cliente.app.models.Animal;
import com.pe.demo.cliente.app.models.services.AnimalService;

import reactor.core.publisher.Mono;

@Component
public class AnimalHandler {

	@Autowired
	private AnimalService service;

	public Mono<ServerResponse> listarTodo(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(service.verTodos(), Animal.class);
	}

	public Mono<ServerResponse> listarId(ServerRequest request) {
		String id = request.pathVariable("id");
		return errorHandler (service.verId(id)
				.flatMap( x -> ServerResponse.ok()
						      .contentType(MediaType.APPLICATION_JSON_UTF8)
						      .body(BodyInserters.fromObject(x))));
				
	}

	public Mono<ServerResponse> borrar(ServerRequest request) {
		String id = request.pathVariable("id");
		return errorHandler (service.eliminar(id).then(ServerResponse.noContent().build()));
				
	}

	public Mono<ServerResponse> guardar(ServerRequest request) {
		Mono<Animal> animal = request.bodyToMono(Animal.class);

		return animal.flatMap(z -> {
			if (z.getFechaRegistro() == null) {
				z.setFechaRegistro(new Date());
			}
			return service.guardar(z);
		}).flatMap(k -> ServerResponse.created(URI.create("/api/client/" + k.getIdAnimal()))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject(k)))
				.onErrorResume(error -> {
					WebClientResponseException errorResponse = (WebClientResponseException) error;
					if (errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
						return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8)
								.syncBody(errorResponse.getResponseBodyAsString());
					}
					return Mono.error(errorResponse);
				});
	}
	
	
	public Mono<ServerResponse> editar(ServerRequest request){
		String id=request.pathVariable("id");
		Mono<Animal> animal=request.bodyToMono(Animal.class);
		
		return errorHandler ( animal.flatMap(z-> service.actualizar(id,z))
				.flatMap(x-> ServerResponse.created(URI.create("/api/client/".concat(id)))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(BodyInserters.fromObject(x))));
	}
	
	public Mono<ServerResponse> upload(ServerRequest request){
		String id=request.pathVariable("id");
		return errorHandler( request.multipartData().map(multipart-> multipart.toSingleValueMap().get("file"))
				.cast(FilePart.class)
				.flatMap(file-> service.upload(file, id))
				.flatMap(k-> ServerResponse.created(URI.create("/api/client/".concat(k.getIdAnimal())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(BodyInserters.fromObject(k)))) ;
	}
	
	private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
		return response.onErrorResume(error-> {
			WebClientResponseException errorResponse = (WebClientResponseException)error;
			if(errorResponse.getStatusCode()==HttpStatus.NOT_FOUND) {
				//return ServerResponse.notFound().build();
				Map<String,Object> bodyArt=new HashMap<>();
				bodyArt.put("error","No existe el producto ".concat(errorResponse.getMessage()));
				bodyArt.put("status",errorResponse.getStatusCode().value());
				return ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(bodyArt);
			}
			return Mono.error(errorResponse);
		});
	}
	
}
