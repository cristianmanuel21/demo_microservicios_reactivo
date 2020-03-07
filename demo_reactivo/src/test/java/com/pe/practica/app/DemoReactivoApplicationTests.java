package com.pe.practica.app;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.pe.practica.app.models.documents.Animal;
import com.pe.practica.app.models.services.AnimalService;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoReactivoApplicationTests {
	
	
	@Autowired 
	private AnimalService service;
	
	@Autowired
	private WebTestClient cliente;
	
	@Test
	void getAll() {
		cliente
		.get()
		.uri("/api/v2/animales")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(Animal.class)
		.consumeWith(consumer->{
			List<Animal> lista=consumer.getResponseBody();
			Assertions.assertThat(lista.size()>0);
		});
	}
	
	@Test
	void getId() {
		Animal animal=service.findByAlias("Peluchin").block();
		cliente
		.get()
		.uri("/api/v2/animales/{id}", Collections.singletonMap("id", animal.getIdAnimal()  ) )
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectBody(Animal.class)
		.consumeWith(consumer->{
			Animal animal2=consumer.getResponseBody();
			Assertions.assertThat(animal2.getAlias()).isEqualTo("Peluchin");
		});
	}
	
	@Test
	void eliminarId() {
		Animal animal=service.findByAlias("Coco").block();
		cliente
		.delete()
		.uri("/api/v2/animales/{id}",Collections.singletonMap("id",animal.getIdAnimal()))
		.exchange()
		.expectStatus().isNoContent()
		.expectBody().isEmpty();
	}
	
	@Test
	void grabarNuevo() {
		Animal animal=service.findByAlias("Condorito").block();
		Animal animal2=new Animal("Gallinazo", 9, "Pedro", animal.getClase());
		cliente
		.post()
		.uri("api/v2/animales")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(animal2), Animal.class)
		.exchange()
		.expectStatus()
		.isCreated()
		.expectBody(Animal.class)
		.consumeWith(consumer->{
			Animal animal3=consumer.getResponseBody();
			Assertions.assertThat(animal3.getNombre()).isEqualTo("Gallinazo");
		});
		
	}
	
	
	 @Test
	 void actualizarUno() {
		 Animal animal=service.findByAlias("Michi").block();
		 Animal animal2=new Animal("gatita", 19, "Waira", animal.getClase());
		 cliente
		 .put()
		 .uri("api/v2/animales/{id}",Collections.singletonMap("id", animal.getIdAnimal()))
		 .accept(MediaType.APPLICATION_JSON_UTF8)
		 .contentType(MediaType.APPLICATION_JSON_UTF8)
		 .body(Mono.just(animal2),Animal.class)
		 .exchange()
		 .expectStatus()
		 .isCreated()
		 .expectBody(Animal.class)
		 .consumeWith(consumer->{
			Animal animal3=consumer.getResponseBody();
			Assertions.assertThat(animal3.getAlias()).isEqualTo("Waira");
		 });
		 
		 
	 }
	
}
