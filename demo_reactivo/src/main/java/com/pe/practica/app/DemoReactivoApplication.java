package com.pe.practica.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.pe.practica.app.models.documents.Animal;
import com.pe.practica.app.models.documents.Clases;
import com.pe.practica.app.models.services.AnimalService;

import reactor.core.publisher.Flux;

@EnableEurekaClient
@SpringBootApplication
public class DemoReactivoApplication implements CommandLineRunner {
	
	private static final Logger log=LoggerFactory.getLogger(DemoReactivoApplication.class);

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Autowired
	private AnimalService service;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoReactivoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("clases").subscribe();
		mongoTemplate.dropCollection("animales").subscribe();
		
		Clases mamifero=new Clases("Mamifero");
		Clases ave=new Clases("Ave");
		Clases reptil=new Clases("Reptil");
		
		Flux.just(mamifero,ave,reptil)
		.flatMap(service::saveClases)
		.doOnNext(c-> {
			log.info("Clases creadas "+c.getNombre()+" "+c.getIdClase());
		}).thenMany(
				Flux.just(new Animal("perro", 7, "Peluchin",  mamifero),
						new Animal("gato", 3, "Michi", mamifero),
						new Animal("loro", 14, "Condorito", ave),
						new Animal("cocodrilo", 7, "Coco",  reptil))
				.flatMap(animal->{
					animal.setFechaRegistro(new Date());
					return service.saveAnimal(animal);
				})
		)
		.subscribe(animal->log.info("grabados "+ animal.getIdAnimal()+" "+animal.getNombre()+" "+animal.getAlias() ));

	}

}
