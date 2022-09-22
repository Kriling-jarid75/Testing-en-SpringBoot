package com.example.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class ProductoTest {

	@Autowired
	private ProductoRepositorio repositorio;

	// Método para guardar algun producto pro nombre
	@Test
	@Rollback(false)
	@Order(1)
	public void testGuardarProducto() {
		Producto producto = new Producto("Laptop", 2000);
		Producto productoGuardado = repositorio.save(producto);
		assertNotNull(productoGuardado);
		System.out.println("***Se guardo el producto correctamente***");
		System.out.println(productoGuardado);
		
	}

	// Método para buscar algun producto por nombre
	@Test
	@Order(2)
	public void testBuscarProductoPorNombre() {
		String nombre = "Televisor full HD";
		Producto producto = repositorio.findByNombre(nombre);
		assertThat(producto.getNombre()).isEqualTo(nombre);
		System.out.println("****Se encontró el producto correctamente***");
		System.out.println(producto);

	}

	// Método para buscar por producto no existente
	@Test
	@Order(3)
	public void testBuscarProductoPorNombreNoExistente() {
		String nombre = " Televisor Samsung HD";
		Producto producto = repositorio.findByNombre(nombre);
		assertNull(producto);
	}

	// Método para actualizar algun producto por ID
	@Test
	@Rollback(false)
	@Order(4)
	public void testActualizarProducto() {
		String nombreProducto = "Televisor HD 200Pull";// el nuevo valor
		Producto producto = new Producto(nombreProducto, 5000);// valores nuevos
		producto.setId(8);// colocar el id a actualizar
		repositorio.save(producto);
		Producto productoActualizado = repositorio.findByNombre(nombreProducto);
		assertThat(productoActualizado.getNombre()).isEqualTo(nombreProducto);
		System.out.println("***Se actualizó el producto correctamente***");
		System.out.println(productoActualizado);
	}

	// Método para listar todos los productos de la base de datos
	@Test
	@Order(5)
	public void testListarProductos() {
		List<Producto> productos = (List<Producto>) repositorio.findAll();
		System.out.println("*****Se listaron los productos correctamente*****");
		for (Producto producto : productos) {

			System.out.println(producto);
		}
		assertThat(productos).size().isGreaterThan(0);
	}

	@Test
	@Rollback(false)
	@Order(6)
	public void testEliminarProducto() {
		Integer id = 9;
		boolean existenteAntesDeEliminar = repositorio.findById(id).isPresent();// devuelve un true

		repositorio.deleteById(id);

		boolean noExisteDespuesDeEliminar = repositorio.findById(id).isPresent();
		assertTrue(existenteAntesDeEliminar);
		assertFalse(noExisteDespuesDeEliminar);
		System.out.println("*****Se eliminó el producto correctamente*****");

	}

}
