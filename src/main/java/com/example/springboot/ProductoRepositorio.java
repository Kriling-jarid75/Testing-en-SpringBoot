package com.example.springboot;

import org.springframework.data.repository.CrudRepository;

public interface ProductoRepositorio extends CrudRepository<Producto, Integer>{
	
	public Producto findByNombre(String nombre);

}
