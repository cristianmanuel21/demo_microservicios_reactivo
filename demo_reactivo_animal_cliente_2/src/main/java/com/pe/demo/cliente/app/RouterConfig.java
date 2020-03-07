package com.pe.demo.cliente.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.pe.demo.cliente.app.handler.AnimalHandler;

@Configuration
public class RouterConfig {

	@Bean
	public RouterFunction<ServerResponse> rutas(AnimalHandler handler) {
		return RouterFunctions.route(RequestPredicates.GET("/api/client"), handler::listarTodo)
							  .andRoute(RequestPredicates.GET("/api/client/{id}"),handler::listarId)
							  .andRoute(RequestPredicates.DELETE("/api/client/{id}"),handler::borrar)
							  .andRoute(RequestPredicates.POST("/api/client"),handler::guardar)
							  .andRoute(RequestPredicates.PUT("/api/client/{id}"),handler::editar)
							  .andRoute(RequestPredicates.POST("/api/client/upload/{id}"),handler::upload);

		
	}

}