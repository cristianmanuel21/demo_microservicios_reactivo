package com.pe.practica.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.pe.practica.app.handler.AnimalHandlerFinal;
import com.pe.practica.app.handler.AnimalHandlerFinalReview;

@Configuration
public class RouterFunctionConfig {
	
	
	
	

	
	/*@Bean
	public RouterFunction<ServerResponse> routes(AnimalHandler handler){
		return route(GET("/api/v2/animales").or(GET("/api/v3/animales")), handler::getAll )
				.andRoute(GET("/api/v2/animales/{id}"), handler::getId )
				.andRoute(DELETE("/api/v2/animales/{id}"), handler::eliminar )
				.andRoute(POST("/api/v2/animales/"),handler::crearFinal)
				.andRoute(PUT("/api/v2/animales/{id}"),handler::actualizar)
		        .andRoute(POST("/api/v2/animales/{id}"),handler::upload);
	}*/
	
	
	/*
	@Bean
	public RouterFunction<ServerResponse> routes(AnimalFinalHandler handler){
		return route(GET("/api/v2/animales").or(GET("/api/v3/animales")), handler::getAll )
			.andRoute(GET("/api/v2/animales/{id}"), handler::getId )
			.andRoute(DELETE("/api/v2/animales/{id}"), handler::borrar )
			.andRoute(POST("/api/v2/animales"), handler::guardarItem)
			.andRoute(PUT("/api/v2/animales/{id}"), handler::editar)
			.andRoute(POST("/api/v2/animales/{id}"), handler::upload);
			
	}
	*/
	
	/*@Bean
	public RouterFunction<ServerResponse> rutas(AnimalHandlerFinal handler){
		return RouterFunctions.route(RequestPredicates.GET("/api/v2/animales"),handler::getAll)
				.andRoute(RequestPredicates.GET("/api/v2/animales/{id}"),handler::getId)
				.andRoute(RequestPredicates.DELETE("/api/v2/animales/{id}"),handler::eliminar)
				.andRoute(RequestPredicates.POST("/api/v2/animales"), handler::guardar)
				.andRoute(RequestPredicates.PUT("/api/v2/animales/{id}"), handler::editar)
				.andRoute(RequestPredicates.POST("/api/v2/animales/upload/{id}"), handler::upload);
				
		
	}*/
	
	
	
	@Bean
	public RouterFunction<ServerResponse> rutas(AnimalHandlerFinalReview handler){
		return RouterFunctions.route(RequestPredicates.GET("/api/v2/animales"),handler::traerTodo)
				.andRoute(RequestPredicates.GET("/api/v2/animales/{id}"),handler::traerId)
				.andRoute(RequestPredicates.POST("/api/v2/animales"),handler::guardar)
				.andRoute(RequestPredicates.DELETE("/api/v2/animales/{id}"),handler::eliminarId)
				.andRoute(RequestPredicates.PUT("/api/v2/animales/{id}"),handler::actualizar)
				.andRoute(RequestPredicates.POST("/api/v2/animales/{id}"),handler::upload);
				/*.andRoute(RequestPredicates.POST("/api/v2/animales"),handler::guardar)
				.andRoute(RequestPredicates.PUT("/api/v2/animales/{id}"),handler::update);*/
	}
	
	
	
	
}
