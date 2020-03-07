package com.pe.practica.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.practica.app.models.dao.AnimalDao;
import com.pe.practica.app.models.dao.ClasesDao;
import com.pe.practica.app.models.documents.Animal;
import com.pe.practica.app.models.documents.Clases;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnimalServiceImpl implements AnimalService{
	
	@Autowired
	AnimalDao animalDao;
	
	@Autowired 
	ClasesDao clasesDao;

	@Override
	public Flux<Animal> findAll() {
		return animalDao.findAll();
	}

	@Override
	public Mono<Animal> findbyId(String id) {
		return animalDao.findById(id);
	}

	@Override
	public Mono<Animal> saveAnimal(Animal animal) {
		return animalDao.save(animal);
	}

	@Override
	public Mono<Void> deleteAnimal(Animal animal) {
		return animalDao.delete(animal);
	}

	@Override
	public Mono<Clases> saveClases(Clases clases) {
		return clasesDao.save(clases);
	}

	@Override
	public Flux<Clases> findAllClases() {
		return clasesDao.findAll();
	}

	@Override
	public Mono<Clases> findByIdClases(String id) {
		return clasesDao.findById(id);
	}

	@Override
	public Mono<Animal> findByAlias(String alias) {
		// TODO Auto-generated method stub
		return animalDao.findByAlias(alias);
	}

	@Override
	public Mono<Clases> findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return clasesDao.findByNombre(nombre);
	}

}
